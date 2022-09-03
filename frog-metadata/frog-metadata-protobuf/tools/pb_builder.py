#! /usr/bin/python
# -*- coding: utf-8 -*-

'''
    用于编译proto文件，使用: pb_builder.py [all]
    1，pb_builder.py all    全编
    2，pb_builder.py        编译有更新的Proto
'''
import os,sys

__author__ = 'wu_hc'


def get_all_proto_files(proto_file_path):
    L=[]
    for root, dirs, files in os.walk(proto_file_path):
        for file in files:
            if os.path.splitext(file)[1] == '.proto':
                L.append(os.path.join(root, file))
    return L

def is_grpc_file(proto_full_path):
    path = os.path.normpath(proto_full_path)
    all_folder = path.split(os.sep)
    for folder in all_folder:
        if(folder == 'grpc'):
            return True
    return False

if __name__ == "__main__":
    #local var
    input_args = sys.argv[1:]
    cur_path = os.getcwd()
    out_path = os.path.join(cur_path,'../','src','main','java')
    proto_file_path = os.path.join(cur_path,'../','proto')

    compile_proto_files = get_all_proto_files(proto_file_path)

    if len(compile_proto_files) == 0:
        print("No proto file modify,If u want to compile all proto file,run: python pb_builder.py all")
    else:
        for f in compile_proto_files:
            is_grpc = is_grpc_file(f)
            cmd = None
            if is_grpc:
                cmd = 'protoc3 --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java-1.45.1-windows-x86_64.exe -I=%s --grpc-java_out=%s %s' % (proto_file_path,out_path,f)
            else:
                cmd = 'protoc -I=%s --java_out=%s %s' % (proto_file_path,out_path,f)
            os.system(cmd)
            print(cmd)
        print ("Generate Finish!!!")