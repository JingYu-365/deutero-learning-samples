#!/usr/bin/env python3
# -*- coding: utf-8 -*-

""" 
    io入门
"""

__author__ = 'JKong'

'''
    open ('文件名','打开的模式')
    r:只读的方式打开，如果文件不存在会提示错误
    w:只写的方式打开，如果文件存在则覆盖，不存在则创建
    a:打开一个文件进行追加内容，如果存在则打开，不存在则创建新的文件

    r+:读写，会将文件指针调到文件的头部
    w+:读写，文件不存在直接创建，存在覆盖源文件
    a+:追加读写，会将文件的指针调到文件的末尾
'''

# 以写的方式打开一个文件
files = open('python.txt', 'w', encoding='utf-8')
content = 'hello 你好'
files.write(content)  # 写入数据
files.close()

print("write end and read begin.")

# 打开文件读取问价内容
files = open('python.txt', 'r')  # 以只读的模式打开
# 输出文件内容
print(files.read())
# 关闭文件
files.close()
