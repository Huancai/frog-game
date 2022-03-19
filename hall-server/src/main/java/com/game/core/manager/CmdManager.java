package com.game.core.manager;

import cn.hutool.core.util.ClassUtil;
import com.game.core.cmd.AbstractCMD;
import com.game.module.misc.GamePlayer;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.me.transport.Cmd;
import com.me.transport.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Component
@Slf4j
public class CmdManager implements InitializingBean, DisposableBean {

    private static final Map<Short, AbstractCMD> cmdMap = new HashMap<>();

    /**
     * @param gamePlayer
     * @param message
     * @return
     */
    public void doExecuteCMD(GamePlayer gamePlayer, Message message) {
        AbstractCMD abstractCMD = cmdMap.get(message.command());
        if (Objects.isNull(abstractCMD)) {
            log.error("unregister cmd code:{} ", message.command());
            return;
        }
        try {
            long cur = System.currentTimeMillis();
            abstractCMD.doExecute(gamePlayer, message);
            int diff = (int) (System.currentTimeMillis() - cur);
            if (diff > 100) {
                log.warn("execute cmd[{}] cost must time:[{}]ms", message.command(), diff);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Class<?>> cmdClasses = ClassUtil.scanPackageByAnnotation("com.game.module", Cmd.class);
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

            cmdMap.put(cmd.code(), cmder);
            log.info("c --> s register ok,cmd:{}->class:{} .", cmd.code(), cmder.getClass());
        }
    }

    @Override
    public void destroy() throws Exception {
        cmdMap.clear();
    }
}
