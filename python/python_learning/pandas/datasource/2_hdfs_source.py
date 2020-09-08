#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    从 HDFS 中读取文件内容
    @see：https://pypi.org/project/PyHDFS/
"""

__author__ = 'JKong'

import pandas as pd
from pyhdfs import HdfsClient

client = HdfsClient(hosts='10.10.27.47:9870', user_name="hdfs")
# TypeError: cannot use a string pattern on a bytes-like object
# 从hdfs中读取文件
file = client.open(r"/a_jkong_test_data/1000.txt")
# 获取内容
content = file.read()
# open后,file是二进制,str()转换为字符串并转码
s = str(content, "utf-8")
# 打开本地文件.csv 并写入内容
file = open("./data/data.csv", "w")
file.write(s)
# pandas读取本地csv文件
train_data = pd.read_csv("./data/data.csv", sep=",", header=None, usecols=[0, 1, 2, 3, 4],
                         names=['id', 'name', 'age', 'gender', 'time'])
print(train_data)
