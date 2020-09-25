#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    数组操作
"""

__author__ = 'JKong'

import numpy as np

# 迭代数组
a = np.arange(6).reshape(2, 3)
for x in np.nditer(a):
    print(x, end=", ")
print()

# 修改数组形状 => reshape
print("========")
a = np.arange(8)
for element in a.flat:
    print(element)

b = a.reshape((4, 2))
for row in b:
    print(row)

print("==== 将数据拉平 ====")
print(b.flatten())
print(b)
# order：
# 'C' -- 按行，
# 'F' -- 按列，
# 'A' -- 原顺序，
# s'K' -- 元素在内存中的出现顺序。
print(b.ravel(order="C"))
print(b.ravel(order="F"))

# 翻转数组
print("==== 翻转数组 ====")
a = np.arange(12).reshape((3, 4))
print(a)
print(np.transpose(a))  # 翻转
print(a.T)  # 等价于 transpose()

# 修改数组维度
print("==== 修改数组维度 ====")
x = np.array([[1], [2], [3]])
y = np.array([4, 5, 6])
b = np.broadcast(x, y)
print(b)
r, c = b.iters
print(next(r), next(c))
print(next(r), next(c))
c = np.empty(b.shape)
c.flat = [u + v for (u, v) in b]
print(c)
print(x + y)

# broadcast_to 函数将数组广播到新形状
a = np.arange(4).reshape((1, 4))
print(np.broadcast_to(a, (4, 4)))

# np.expand_dims 函数通过在指定位置插入新的轴来扩展数组形状
print("========")
x = np.array([[1, 2], [3, 4]])
y = np.expand_dims(x, axis=0)
print(y)
y = np.expand_dims(x, axis=1)
print(y)

# numpy.squeeze 函数从给定数组的形状中删除一维的条目
print("==== numpy.squeeze ===")
x = np.arange(9).reshape(1, 3, 3)
print(x)
y = np.squeeze(x)
y = np.squeeze(y)
print(y.shape)

# np.concatenate 连接数组
print("==== np.concatenate 连接数组 ====")
a = np.array([[1, 2], [3, 4]])
b = np.array([[5, 6], [7, 8]])
# 沿轴 0 连接两个数组
print(np.concatenate((a, b), axis=0))
# 沿轴 1 连接两个数组
print(np.concatenate((a, b), axis=1))

# np.stack 函数用于沿新轴连接数组序列
print("==== np.stack ====")
a = np.array([[1, 2], [3, 4]])
b = np.array([[5, 6], [7, 8]])
# 沿轴 0 堆叠两个数组
print(np.stack((a, b)))
# 沿轴 1 堆叠两个数组
print(np.stack((a, b), axis=1))

# np.hstack 是 numpy.stack 函数的变体，它通过水平堆叠来生成数组。
print("==== np.hstack ====")
a = np.array([[1, 2], [3, 4]])
b = np.array([[5, 6], [7, 8]])
# 水平堆叠
c = np.hstack((a, b))
# 竖直堆叠
d = np.vstack((a, b))
print(c)
print(d)

# 5. 分割数组
print("========")
a = np.arange(9)
b = np.split(a, 3)
c = np.split(a, [4, 7])
print(b)
print(c)
