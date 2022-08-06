package com.me.game.common.manager;

import cn.hutool.core.util.ClassUtil;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Parser;
import com.me.common.net.Cmd;
import com.me.common.worker.FrogGlobalExecutor;
import com.me.game.common.cmd.AbstractCMD;
import com.me.game.common.cmd.CmderTask;
import com.me.game.common.cmd.CmderWrap;
import com.me.game.module.misc.data.GamePlayer;
import com.me.transport.api.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Component
@Slf4j
public class CmdManager implements InitializingBean, DisposableBean {

    private static final Map<Short, CmderWrap> cmdMap = new HashMap<>();
    private static final Map<Short, Method> cmdMethodMap = new HashMap<>();

    @Autowired
    Environment environment;

    /**
     * @param gamePlayer
     * @param message
     * @return
     */
    public void doExecuteCMD(GamePlayer gamePlayer, Message message) {
        CmderWrap wrap = cmdMap.get(message.command());
        if (Objects.isNull(wrap)) {
            log.error("unregister cmd code:{} ", message.command());
            return;
        }

        final Runnable cmderTask = new CmderTask(gamePlayer, wrap.getCmder(), message);
        if (wrap.isAsync() || Objects.isNull(gamePlayer)) {
            FrogGlobalExecutor.invokerAsync(cmderTask);
        } else {
            gamePlayer.runInLoop(cmderTask);
        }
    }

    public void doExecuteCMD_(AbstractCMD cmder, GamePlayer gamePlayer, Message message) {
        try {
            long cur = System.currentTimeMillis();
            cmder.doExecute(gamePlayer, message);
            int diff = (int) (System.currentTimeMillis() - cur);
            if (diff > 100) {
                log.warn("execute cmd[{}] cost must time:[{}]ms", message.command(), diff);
            }
        } catch (Exception e) {
            log.error("Cmder doExecute error", e);
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String scanPackage = environment.getProperty("cmd-scan-package", "com.me.game.module");
        log.info("CmdManager scanPackage:{}", scanPackage);
        Set<Class<?>> cmdClasses = ClassUtil.scanPackageByAnnotation(scanPackage, Cmd.class);
        for (Class<?> cmdClass : cmdClasses) {
            Cmd cmd = cmdClass.getAnnotation(Cmd.class);
            if (Objects.isNull(cmd)) {
                throw new RuntimeException("cmd parse error 1:" + cmdClass);
            }
            AbstractCMD<? extends GeneratedMessage> cmder = (AbstractCMD<? extends GeneratedMessage>) cmdClass.newInstance();
            Class<?> clazz = cmdClass;
            while (clazz != Object.class) {
                Type t = clazz.getGenericSuperclass();
                if (t instanceof ParameterizedType) {
                    Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                    if (args[0] instanceof Class) {
                        clazz = (Class<?>) args[0];
                        Parser<? extends GeneratedMessage> parser;
                        parser = (Parser<? extends GeneratedMessage>) ((GeneratedMessage) clazz.getMethod("getDefaultInstance").invoke(null))
                                .getParserForType();
                        cmder.setParser(parser);
                        break;
                    }
                }
                clazz = clazz.getSuperclass();

            }
            if (clazz == Object.class) {
                throw new RuntimeException("cmd parse error 2:" + cmdClass);
            }

            cmdMap.put(cmd.code(), new CmderWrap(cmder, cmd));

            cmdMethod(clazz);
            log.info("c --> s register ok,cmd:{}->class:{} .", cmd.code(), cmder.getClass());
        }
    }

    @Override
    public void destroy() throws Exception {
        cmdMap.clear();
    }

    private void cmdMethod(Class clazz) {
        for (Method method : findAllMethod(clazz)) {
            Cmd annotation = AnnotationUtils.findAnnotation(method, Cmd.class);
            if (Objects.nonNull(annotation)) {
                cmdMethodMap.put(annotation.code(), method);
            }
        }
    }

    private List<Method> findAllMethod(Class clazz) {
        final List<Method> res = new LinkedList<>();
        ReflectionUtils.doWithMethods(clazz, method -> res.add(method));
        return res;
    }
}
