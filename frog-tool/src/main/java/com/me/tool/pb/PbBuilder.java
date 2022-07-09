package com.me.tool.pb;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public class PbBuilder {

    public static void main(String[] args) {
        List<File> protoFiles = get_all_proto_files("proto");
        if (protoFiles.isEmpty()) {
            return;
        }
        String userDir = System.getProperty("user.dir");
        String outPath = StrUtil.format("{}/frog-metadata/frog-metadata-protobuf/src/main/java", userDir);

        for (File protoFile : protoFiles) {
            String cmd = StrUtil.format("{}/protoc -I={} --java_out={} {}", FileUtil.getAbsolutePath("proto"),FileUtil.getAbsolutePath("proto"), outPath, protoFile.getAbsolutePath());
            System.out.println(cmd);
            try {
                Process exec = Runtime.getRuntime().exec(cmd);
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
