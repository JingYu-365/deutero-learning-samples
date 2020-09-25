#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    Learn Numpy
"""

__author__ = 'JKong'

import numpy as np

"""
    创建数组
"""
# 1. 创建一维数组
arr_1 = np.array([1, 2, 3])
print(arr_1)

# 2. 创建二维数组
arr_2 = np.array([[1, 2, 3], [2, 3, 4]])
print(arr_2)

# 3. np.empty
x = np.empty((3, 2), dtype=np.int)
print(x)

# 4. np.zeros
x = np.zeros(3, dtype=np.int)
print(x)
x = np.zeros((3, 3), dtype=np.float)
print(x)

# 5. np.ones
x = np.ones(3)
print(x)

x = np.ones((2, 3, 4), dtype=np.int)
print(x)

print("========================")
"""
    从已有数组创建数组
"""

# 1. 将列表转为 ndarray
list_1 = [1, 2, 3, 4, 5]
arr_3 = np.asarray(list_1)
print(arr_3)

# 如果列表中的子列表的元素数量不同，则会转为低一维的list ndarray
list_2 = [[1, 2, 3, 4], [2, 3, 4, 5]]
arr_4 = np.asarray(list_2, dtype=np.float)
print(arr_4)

# 2. 创建相同shape的ndarray
arr_5 = np.ones_like(list_1)
print(arr_5)

arr_6 = np.ones_like(list_2)
print(arr_6)

arr_7 = np.full_like(list_2, 2)
print(arr_7)

arr_8 = np.zeros_like(list_2)
print(arr_8)

arr_9 = np.empty_like(list_2)
print(arr_9)

print("====================")

"""
    从数值范围创建数组
"""
# 1. arrange : 根据 start 与 stop 指定的范围以及 step 设定的步长，生成一个 ndarray。
# ==> numpy.arange(start, stop, step, dtype)
arr_1_1 = np.arange(0, 10, 1, np.int)
print(arr_1_1)

# 2. linspace : 创建一个一维数组，数组是一个等差数列构成的
# ==> np.linspace(start, stop, num=50, endpoint=True, retstep=False, dtype=None)
arr_1_2 = np.linspace(1, 10, 10, True, False, np.int)
print(arr_1_2)

# 3. reshape
arr_1_3 = np.linspace(1, 10, 10) \
    .reshape((2, 5))  # 将 1*10 转为 2*5
print(arr_1_3)
