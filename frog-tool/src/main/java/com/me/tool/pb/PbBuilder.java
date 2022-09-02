package com.me.tool.pb;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public class PbBuilder {

    public static void main(String[] args) {


        String userDir = System.getProperty("user.dir");
        String protobufProjPath = StrUtil.format("{}/frog-metadata/frog-metadata-protobuf", userDir);
        String protoPath = StrUtil.format("{}/proto/grpc", protobufProjPath);
        String proto2CmdPath = StrUtil.format("{}/tools/protoc.exe", protobufProjPath);
        String proto3CmdPath = StrUtil.format("{}/tools/protoc3.exe", protobufProjPath);
        String protoOutPath = StrUtil.format("{}/src/main/java", protobufProjPath);

        String grpcPlugin = StrUtil.format("{}/tools/protoc-gen-grpc-java-1.45.1-windows-x86_64.exe", protobufProjPath);

        List<File> protoFiles = get_all_proto_files(protoPath);
        for (File protoFile : protoFiles) {
            String cmd = null;
            if (isGrpcProtoFile(protoFile)) {
                cmd = StrUtil.format("{} --plugin=protoc-gen-grpc-java={} -I={} --grpc-java_out={} {}", proto3CmdPath, grpcPlugin, protoPath, protoOutPath, protoFile.getAbsolutePath());
            } else {
                cmd = StrUtil.format("{} -I={} --java_out={} {}", proto2CmdPath, protoPath, protoOutPath, protoFile.getAbsolutePath());
            }
            System.out.println(cmd);
//            RuntimeUtil.exec(cmd);
            try {
                Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<File> get_all_proto_files(String proto_file_path) {
        List<File> files = FileUtil.loopFiles(proto_file_path, file -> {
            String suffix = FileUtil.getSuffix(file);
            return StrUtil.equals("proto", suffix);
        });

        return files;
    }

    private static boolean isGrpcProtoFile(File protoFile) {
        boolean ret = false;
        List<String> readLines = FileUtil.readLines(protoFile, Charset.defaultCharset());
        for (String readLine : readLines) {
            if (StrUtil.startWith(readLine, "syntax")) {
                ret = StrUtil.containsIgnoreCase(readLine, "proto3");
                break;
            }
        }
        return ret;
    }
}
