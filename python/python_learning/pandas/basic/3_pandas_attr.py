#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    Pandas 相关属性
"""

__author__ = 'JKong'

import numpy as np
import pandas as pd

'''
     基础属性
'''
d = {'Name': pd.Series(['Tom', 'James', 'Ricky', 'Vin', 'Steve', 'Minsu', 'Jack']),
     'Age': pd.Series([25, 26, 25, 23, 30, 29, 23]),
     'Rating': pd.Series([4.23, 3.24, 3.98, 2.56, 3.20, 4.6, 3.8])}
df = pd.DataFrame(d)
print("======== 带属性及索引输出数据 ========")
print(df)
print(df.axes)
print("======== 属性及类型 ========")
print(df.dtypes)

print("======== 维度 ========")
print(df.ndim)

print("======== 结构？ ========")
print(df.shape)

print("======== 数量 ========")
print(df.size)

print("======== 数据 ========")
print(df.values)

'''
     统计
          
     index，即 axis=0，默认值
     columns, 即 axis=1
'''
print("======== 按照列求和 ========")
print(df.sum())
print("======== 按照行求和 ========")
print(df.sum(1))

print("======== 按照 列 求平均 ========")
print(df.mean())
print("======== 按照 行 求平均 ========")
print(df.mean(1))

print("======== 按照 列 求标准差 ========")
print(df.std())
print("======== 按照 行 求标准差 ========")
print(df.std(1))
