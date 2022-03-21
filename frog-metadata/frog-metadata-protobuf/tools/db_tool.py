#! /usr/bin/python
# -*- coding: utf-8 -*-

'''
    com.me.metadata.db#table -> java#model
'''

import os,re,time
import pymysql,shutil

__version__ = '1.1.1'
__author__ = 'wu_hc'

remark_string = '// Generated by the db_tool.exe,  DO NOT EDIT!\n\n'
package_string = 'package com.database.entities;'
import_string = '\n\nimport java.util.Objects;';
class_string_1 = '\n\n/**\n * %s \n */\npublic final class %s extends AbsEntity {\n\n'
class_string_2 = '\t/**\n\t * serial uid\n\t */\n\tprivate static final long serialVersionUID = 1L;\n\n'
class_string_3 = '}'

setter_string_1 = '\t/**\n\t * %s\n\t */\n\tpublic void set%s(%s %s) {\n\t\tthis.%s = %s;\n\t}\n\n'
setter_string_2 = '\t/**\n\t * %s\n\t */\n\tpublic void set%s(%s %s) {\n\t\tif (Objects.equals(this.%s, %s))\n\t\t\treturn;\n\t\tthis.%s = %s;\n\t\tsetUpdate(true);\n\t}\n\n'
getter_string = '\t/**\n\t * %s\n\t */\n\tpublic %s get%s() {\n\t\treturn this.%s;\n\t}\n\n'
var_string = '\t/**\n\t * %s\n\t */\n\tprivate %s %s;\n\n'



class Table(object):
    def __init__(self, cls_name,db_name,table_name):
       self.cls_name = cls_name #变量名称
       self.note = ("database:%s,table:%s" % (db_name,table_name)) #注释
       self.table_name = table_name
       self.var_L = []

    def append_var(self,var):
        self.var_L.append(var)

    def to_insert_sql(self):
        s = '('
        sql = 'INSERT INTO %s' % (self.table_name)
        for v in self.var_L:
            s +=  "`" + (v.name) + "`" + ','
        s = s[:-1]
        s += ')'
        s += ' VALUES '
        sql += s
        s = '('
        for v in self.var_L:
            s += '#{' + v.name + '}' + ','
        s = s[:-1]
        s += ')'
        sql += s
        return sql

    def to_update_sql(self):
        sql = 'UPDATE %s SET ' % (self.table_name)
        cdt = ' WHERE '
        pri_L = []
        for v in self.var_L:
            if v.is_pri:
                cdt +=  ("`" + (v.name) + "`" + '=#{' + (v.name) + '}') + ' and '
            else:
                sql +=  ("`" + (v.name) + "`" + '=#{' + (v.name) + '}') + ','

        sql = sql[:-1]
        cdt = cdt[:-4]
        return sql + cdt

    def to_select_sql(self):
        return 'SELECT * FROM  %s' % (self.table_name)

    def to_delete_sql(self):
        sql = 'DELETE FROM %s ' % (self.table_name)
        cdt = ' WHERE '
        for v in self.var_L:
            if v.is_pri:
                cdt +=  ("`" + (v.name) + "`" + '=#{' + (v.name) + '}') + ' and '

        cdt = cdt[:-4]
        return sql + cdt

    def to_class_model(self):
        ret = ''

        ret += remark_string
        ret += package_string
        ret += import_string
        ret += (class_string_1 % (self.note,self.cls_name)) #time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())),
        ret += class_string_2
        for var in self.var_L:
            ret += var.to_var()
        for var in self.var_L:
           ret += var.to_setter_2()
           ret += var.to_getter()
        ret += class_string_3

        ret += '\n\n// SQL:\n'
        ret += '// ' + self.to_insert_sql() + '\n'
        ret += '// ' + self.to_update_sql() + '\n'
        ret += '// ' + self.to_select_sql() + '\n'
        ret += '// ' + self.to_delete_sql() + '\n'
        return ret

class Var(object):
    def __init__(self, name,type,note,is_pri):
        self.name = name    #变量名称
        self.note = note    #注释
        self.type = type    #变量类型
        self.is_pri = is_pri #是否主健

    def to_java_type(self):
        if(self.type == 'tinyint' or self.type == 'bool' or self.type == 'bit'):
            return 'boolean'
        elif(self.type == 'bigint'):
            return 'long'
        elif(self.type == 'smallint'):
            return 'int'
        elif(self.type == 'varchar' or self.type == 'text' or self.type == 'char'):
            return 'String'
        elif(self.type == 'datetime' or self.type == 'date'):
            return 'java.util.Date'
        elif (self.type == 'blob' or self.type == 'longblob' or self.type == 'mediumblob'):
            return 'byte[]'
        elif(self.type == 'decimal'):
            return 'java.math.BigDecimal'
        else:
            return self.type

    def to_setter_1(self):
        return (setter_string_1 % (self.note,self.name[0].upper() + self.name[1:],self.to_java_type(),self.name,self.name,self.name))

    def to_setter_2(self):
        return (setter_string_2 % (self.note,self.name[0].upper() + self.name[1:],self.to_java_type(),self.name,self.name,self.name,self.name,self.name))

    def to_getter(self):
        return (getter_string % (self.note,self.to_java_type(),self.name[0].upper() + self.name[1:],self.name))
    def to_var(self):
        return (var_string % (self.note,self.to_java_type(),self.name))

def main_ui():
    print('==============================')
    print(' 1,   show table             |')
    print(' 2,   build java model       |')
    print(' 3,   reload table           |')
    print(' q,   byte byte              |')
    print('==============================')

def reload_db(cursor):
    cursor.execute("show tables")
    results=cursor.fetchall()
    D = {}
    for row in results:
        D[row[0]] = 1
    return D

def show_table(table_D):
    for table in table_D:
        print(table)

def build_java_model(db_name,table_name,cursor,dir):
    java_clz_name = ''
    arr = table_name.split('_');
    for temp in arr:
        if len(temp) == 1:
            continue
        java_clz_name += temp.capitalize()

    java_clz_name += 'Entity'
    java_file_name = java_clz_name + '.java'

    cursor.execute("select column_name,data_type,column_comment,COLUMN_KEY from information_schema.columns where table_schema='%s' and table_name='%s'" % (db_name,table_name))
    results = cursor.fetchall()
    table = Table(java_clz_name,db_name,table_name)

    for row in results:
        var = Var(row[0],row[1],row[2],row[3] == 'PRI' and True or False)
        table.append_var(var)

    java_file_full_path = os.path.join(dir,java_file_name)
    with open(java_file_full_path,'w',encoding='utf-8') as f:
        f.write(table.to_class_model())

    print('MODEL:')
    print(table.to_class_model())
def parse_cfg():
    config = {}
    config['debug'] = False
    cfg_file_path = os.path.join(os.path.dirname(os.path.abspath("__file__")),'config.properties')
    with open(cfg_file_path,'r',encoding='utf-8') as f:
        lines = f.readlines()
        for line in lines:
            line = re.sub(r'\s+', '', line)
            if(not (line) or line.startswith('#')):
                continue
            kv = line.split(r'=')
            config[kv[0]] = kv[1]
    config['author'] = __author__
    config['version'] = __version__

    return config

def key_board_call(event):
    print(event.keysym)  #打印按下的键值


def main():
    try:
        cfg = parse_cfg()
        global CFG
        CFG = cfg
        print(cfg)
        com.me.metadata.db = pymysql.connect(cfg['jdbc_ip'],cfg['jdbc_username'],cfg['jdbc_password'],cfg['jdbc_db']);
        cursor = com.me.metadata.db.cursor()
        table_D = reload_db(cursor)
        is_build = False
        while True:
            main_ui()
            cmd = input(is_build and "Enter table name: " or "Enter your input: ");
            if cmd == '1':
                show_table(table_D)
            elif cmd == '2':
                is_build = True
            elif cmd == '3':
                table_D = reload_db(cursor)
            elif cmd == 'q' or 'i' == 'Q':
                break
            else:
                if is_build and cmd in table_D:
                    build_java_model(cfg['jdbc_db'],cmd,cursor,cfg['out_dir'])
                    is_build = False
                else:
                    print('\n【  input error ,please ensure and try again!  】\n')
        if os.path.exists(sys._MEIPASS):
            shutil.rmtree(sys._MEIPASS)
            print('temp file has been remove!')
        print('bye bye!')
    except Exception as err:
        if CFG['debug'] == 'True' or CFG['debug'] == 'true':
            print(err)
    finally:
        com.me.metadata.db.close()

if __name__ == "__main__":
    main()