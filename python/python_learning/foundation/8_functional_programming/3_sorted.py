#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Author: JKong
# @Time: 2020/7/23 11:45 上午
# @Desc: Python内置的sorted()函数就可以对list进行排序

# 默认是升序排序
print(sorted([36, 5, -12, 9, -21]))

# 降序排序
print(sorted([36, 5, -12, 9, -21], reverse=True))

# 指定排序规则
print(sorted([36, 5, -12, 9, -21], key=abs))
print(sorted(['mmm', 'mm', 'mm', 'm'], key=len))
