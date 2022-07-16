package com.me.tool.pb;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public class PbBuilder {

    public static void main(String[] args) {


        String userDir = System.getProperty("user.dir");
        String protobufProjPath = StrUtil.format("{}/frog-metadata/frog-metadata-protobuf", userDir);
        String protoPath = StrUtil.format("{}/proto", protobufProjPath);
        String protoCmdPath = StrUtil.format("{}/protoc-2.5.0-win32/protoc.exe", protobufProjPath);
        String protoOutPath = StrUtil.format("{}/src/main/java", protobufProjPath);

        List<File> protoFiles = get_all_proto_files(protoPath);
        for (File protoFile : protoFiles) {
            String cmd = StrUtil.format("{} -I={} --java_out={} {}", protoCmdPath, protoPath, protoOutPath, protoFile.getAbsolutePath());
            System.out.println(cmd);
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
}
