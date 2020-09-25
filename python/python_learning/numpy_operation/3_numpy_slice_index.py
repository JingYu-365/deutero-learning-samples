#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    numpy 实现 数组切片及索引操作
"""

__author__ = 'JKong'

import numpy as np

# 1.切片
a = np.array([[1, 2, 3], [3, 4, 5], [6, 7, 8]])
print(a[1:])
print(a[:, 1:])
print(a[..., 1:])
print(a[1, 1:])
print(a[1:, 2])
print(a[..., 2])

# 2. 索引
print("==========")
print(a[2, 2])

# 3. 高级索引
print("==========")
x = np.array([[1, 2], [3, 4], [5, 6]])
'''
console:
    [[1 2]
     [3 4]
     [5 6]]
'''
y = x[[0, 1, 2], [0, 1, 0]]  # 输出坐标为(0,0) (1,1) (2,0)的数据
print(y)

print("==========")

x = np.array([[0, 1, 2], [3, 4, 5], [6, 7, 8], [9, 10, 11]])
print(x)
rows = np.array([[0, 0], [3, 3]])
print(rows)
cols = np.array([[0, 2], [0, 2]])
print(cols)
y = x[rows, cols]
print(y)

print("=========")

x = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
print(x)
a = x[1:3, 1:3]
print(a)
b = x[1:3, [1, 2]]
print(b)
c = x[..., 1:]
print(c)

# 布尔索引
print("=========")
x = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
print(x[x > 5])
print(x[x % 2 == 0])
a = np.array([np.nan, 1, 2, np.nan, 3, 4, 5])
print(a[~np.isnan(a)])

# 花式索引
print("=========")
x = np.arange(32).reshape((8, 4))
print(x)
print(x[[0, 1, 2, 3]])  # 输出第 0,1,2,3 的向量
print(x[[5, 2, 4, 7]])
