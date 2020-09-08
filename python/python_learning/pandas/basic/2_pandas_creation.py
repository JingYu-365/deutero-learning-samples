#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    Pandas 创建
"""

__author__ = 'JKong'

import numpy as np
import pandas as pd

"""
    创建 系列 series
"""
print("========== 创建 系列 series ==========")
s = pd.Series([1, 2, 3, 4, 5], index=['a', 'b', 'c', 'd', 'e'])
# 数据为 [1, 2, 3, 4, 5]， 索引为 ['a', 'b', 'c', 'd', 'e']
print(s)
print(s['e'])
print(s[['a', 'c', 'd']])
print(s[0])

"""
    数据帧
"""
print("========== 通过 一维 列表创建 DataFrame ==========")
# 通过列表创建
data = [1, 2, 3, 4, 5]
df = pd.DataFrame(data)
print(df)

print("========== 通过 二维 列表创建 DataFrame ==========")
data = [['Alex', 10], ['Bob', 12], ['Clarke', 13]]
df = pd.DataFrame(data, columns=['Name', 'Age'])
print(df)

data = [{'a': 1, 'b': 2}, {'a': 5, 'b': 10, 'c': 20}]
df = pd.DataFrame(data)
print(df)

print("========== 通过 字典 创建 DataFrame ==========")
# 通过字典创建
data = {'Name': ['Tom', 'Jack', 'Steve', 'Ricky'], 'Age': [28, 34, 29, 42]}
df = pd.DataFrame(data)
print(df)

# 通过 series 创建
print("========== 通过 series 创建 DataFrame ==========")
d = {'one': pd.Series([1, 2, 3], index=['a', 'b', 'c']),
     'two': pd.Series([1, 2, 3, 4], index=['a', 'b', 'c', 'd'])}
df = pd.DataFrame(d)
print(df)

# 列的添加
print("========== 添加字段 ==========")
df["three"] = pd.Series([10, 20, 30, 40], index=["a", "b", "c", "d"])
print(df)
df["four"] = df["one"] + df["three"]
print(df)

# 列的删除
print("========== 删除字段 ==========")
del df["four"]
print(df)
print(df.iloc[1])
