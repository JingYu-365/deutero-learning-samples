#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Author: JKong
# @Time: 2020/7/23 11:39 上午
# @Desc: Python内建的filter()函数用于过滤序列。

# 过滤偶数
def is_odd(n):
    return n % 2 == 1


print(list(filter(is_odd, [1, 2, 4, 5, 6, 9, 10, 15])))


# 空字符串删掉
def not_empty(s):
    return s and s.strip()


print(list(filter(not_empty, ['A', '', 'B', None, 'C', '  '])))
