#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    Pandas 入门操作
"""

__author__ = 'JKong'

import pandas as pd
import numpy as np

'''
    创建对象
'''
print("=============== START =================")
# 用值列表生成 Series
s = pd.Series([1, 2, 3, 4, np.nan, 5, 6, 7])
print(s)

print("=======================================")
df = pd.DataFrame(np.random.randn(7, 4), index=list("1234567"), columns=list('ABCD'))
print(df)

print("=======================================")
df = pd.DataFrame({'A': 1.,
                   'B': pd.Series(1, index=list(range(4)), dtype='float32'),
                   'C': np.array([3] * 4, dtype='int32'),
                   'D': pd.Categorical(["test", "train", "test", "train"]),
                   'E': 'foo',
                   'F': [1, 2, 3, 4]})
print(df)

print("=======================================")
# 用含日期时间索引与标签的 NumPy 数组生成 DataFrame
dates = pd.date_range('20130101', periods=6)
print(dates)

'''
    查看数据
'''
print("=======================================")
print(df.head(2))
print(df.tail(2))

print(df.index)
print(df.columns)
print(df.values)
print(df.describe())
print("=======================================")
# 翻转
print(df.T)
# 轴排序
print(df.sort_index(axis=0, ascending=False))
# 字段排序
print(df.sort_values(by="F"))

''''
    区块选择
'''
print("=============== 区块选择 =================")
dates = pd.date_range('20170101', periods=6)
df = pd.DataFrame(np.random.randn(6, 4), index=dates, columns=list('ABCD'))
print(df)
# 选择 A B 两列
print(df[["A", "B"]])
# 选择前三行
print(df[0:3])
#
print(df.loc[:, "A":"D"])
print()
print(df.loc["20170102":"20170104", ["A", "D"]])
print(df.loc["20170102", ["A", "D"]])

print(df.iloc[3])
print(df.iloc[1:2, 0:2])
print(df.iloc[[1, 2], [0, 2]])
print(df.iloc[:, 0:2])
print(df.iloc[0:2, :])
print(df.iloc[1, 1])
print(df.iat[1, 1])

'''
    布尔索引
'''
print(df[df.A > 0])
print(df[df.B > 0])
