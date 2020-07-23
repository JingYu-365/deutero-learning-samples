#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Author: JKong
# @Time: 2020/7/23 11:07 上午
# @Desc: 列表生成式

# 生成递增list
print(list(range(1, 11)))  # [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

# 生成幂次方list
print(list(x ** 2 for x in range(1, 11)))

# 添加过滤条件，只生成偶数的幂次方list，此时if只是一个过滤条件，是不可以添加else条件的
print(list(x ** 2 for x in range(1, 11) if x % 2 == 0))
# 如果是奇数则使用0替代，此时 for 前面的部分是一个表达式，它必须根据 x 计算出一个结果，所以需要else条件。
print(list(x ** 2 if x % 2 == 0 else 0 for x in range(1, 11)))

# 如何生成全排列
print(list(m + n for m in 'ABC' for n in 'XYZ'))

# 罗列出当前文件夹下的文件
import os

file = [d for d in os.listdir('.')]  # os.listdir可以列出文件和目录
print(file)
