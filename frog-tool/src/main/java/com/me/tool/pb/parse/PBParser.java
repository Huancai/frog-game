package com.me.tool.pb.parse;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.me.tool.pb.bean.ProtobufFieldDesc;
import com.me.tool.pb.bean.ProtobufMsgDesc;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class PBParser {
    public static void main(String[] args) {

        final String SLIT_SYMBOL = "@";

        Pattern p = Pattern.compile("\\s+");

        Pattern p1 = Pattern.compile("@=@\\d+;");

        LinkedList<ProtobufMsgDesc> msgDescList = new LinkedList<>();

        StrBuilder strBuilder = new StrBuilder();
        List<String> strings = FileUtil.readLines("proto/test/test.proto", Charset.defaultCharset());
        for (String string : strings) {
            String str = string.trim();
            if (StrUtil.isBlank(str)) {
                continue;
            }
            if (str.startsWith("//")) {
                strBuilder.append(str.substring(2, str.length() - 1));
                continue;
            }

            String[] split1 = str.split("//");
            if (split1.length >= 2) {
                strBuilder.append(split1[1]);
            }

            Matcher m = p.matcher(str);
            str = m.replaceAll(SLIT_SYMBOL);

            System.out.println(str);
            if (str.startsWith("message") || str.startsWith("enum")) {
                List<String> split = StrUtil.split(str, SLIT_SYMBOL);
                String name = split.get(1);
                ProtobufMsgDesc protobufMsgDesc = new ProtobufMsgDesc();
                protobufMsgDesc.setMsgName(name);
                protobufMsgDesc.setEnum(str.startsWith("enum"));
                if (strBuilder.length() > 0) {
                    protobufMsgDesc.setComment(strBuilder.toString());
                    strBuilder.clear();
                }
                msgDescList.offer(protobufMsgDesc);
//                continue;
            }

            if (p1.matcher(str).find()) { //字段
                List<String> split = StrUtil.split(str, SLIT_SYMBOL);
                ProtobufFieldDesc fieldDesc = new ProtobufFieldDesc();

                ProtobufMsgDesc peek = msgDescList.peekLast();
                if (peek.isEnum()) {
                    fieldDesc.setType(split.get(0));
                    fieldDesc.setFieldName(split.get(1));
                } else {
                    fieldDesc.setModifier(split.get(0));
                    fieldDesc.setType(split.get(1));
                    fieldDesc.setFieldName(split.get(2));
                    fieldDesc.setSeqId(Integer.parseInt(split.get(4).substring(0,split.get(4).length() -1)));
                }
                if (strBuilder.length() > 0) {
                    fieldDesc.setComment(strBuilder.toString());
                    strBuilder.clear();
                }
                peek.appendField(fieldDesc);
            }

            if (str.endsWith("}")) { //一个完整的msg
                ProtobufMsgDesc poll = msgDescList.pollLast();
                System.out.println(poll);
            }
        }

        System.out.println(msgDescList.size());
    }
}
