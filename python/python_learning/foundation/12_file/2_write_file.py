#!/usr/bin/env python3
# -*- coding: utf-8 -*-

""" 
    写文件
"""

__author__ = 'JKong'


# 使用 每一次写入文件前，打印文件内容
def read(func):
    def wrapper(*args, **kw):
        with open('python.txt', 'r') as file:
            print(file.read())
        return func(*args, **kw)

    return wrapper


def test_w():
    # 以写的方式打开一个文件
    files = open('python.txt', 'w', encoding='utf-8')
    content = 'hello,hello'
    files.write(content)  # 写入数据
    files.close()


@read
def test_a():
    files = open('python.txt', 'a', encoding='utf-8')
    content = "你好,你好"
    files.write(content)
    files.close()


# 当使用 r+ 写中文时会抛出异常
# r+模式（读写）下，如果文件内容已经存在了中文，当你试图插入新内容时，必须使新内容的总体字节数是当前编码下单个汉字占位字节的整数倍。否则读取时会报错。
@read
def test_r_plus():
    with open('python.txt', 'r+') as files:
        content = 'asdasdasd'
        files.write(content)


@read
def test_w_plus():
    with open('python.txt', 'w+', encoding='utf-8') as files:
        content = '不存在则创建，存在则重写 '
        files.write(content)


@read
def test_a_plus():
    with open('python.txt', 'a+', encoding='utf-8') as files:
        content = ' 在尾部追加'
        files.write(content)


@read
def test_empty():
    pass


if __name__ == '__main__':
    test_w()
    test_a()
    test_r_plus()
    test_w_plus()
    test_a_plus()

    test_empty()
