#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Author: JKong
# @Time: 2020/7/23 10:54 上午
# @Desc: 迭代器操作

# 迭代dict
print("====== Dict =======")
a = {'a': 1, 'b': 2, 'c': 3}
# 迭代key
for key in a.keys():
    print(key)

# 迭代values
for value in a.values():
    print(value)

# 同时迭代 key 与 value
for key, value in a.items():
    print(key, ":", value)

# 迭代list
print("====== List =======")
b = ["JKong", 1, True]
for item in b:
    print(item)

# 根据下标进行遍历
for i, _ in enumerate(b):
    print(b[i])

# 迭代 String
print("====== String =======")
c = "JKong"
for ch in c:
    print(ch)
