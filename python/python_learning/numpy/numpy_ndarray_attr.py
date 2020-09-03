#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    ndarray 属性
"""

__author__ = 'JKong'

import numpy as np

x = np.ones((2, 3, 4), dtype=np.int)

# 1. ndarray.ndim 秩，即轴的数量或维度的数量
print(x.ndim)

# 2. ndarray.shape 数组的维度，对于矩阵，n行m列
print(x.shape)

# 3.ndarray.size 数组元素的总个数，相当于 .shape 中 n*m 的值
print(x.size)  # 2 * 3 * 4 = 24
