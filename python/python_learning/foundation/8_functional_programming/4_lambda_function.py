#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Author: JKong
# @Time: 2020/7/23 2:02 下午
# @Desc: 匿名函数

print(list(map(lambda x: x * x, [1, 2, 3, 4, 5, 6, 7, 8, 9])))

f = lambda x: x * x
print(f(10))
