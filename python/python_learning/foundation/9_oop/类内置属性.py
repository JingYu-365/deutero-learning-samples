#!/usr/bin/env python3
# -*- coding: utf-8 -*-

""" 
    类的内置属性
"""

__author__ = 'JKong'


class Student(object):
    # 在类中声明类属性
    country = 'China'

    # 在构造方法中设声明例属性
    def __init__(self, name, score):
        self.name = name
        self.score = score

    def get_grade(self):
        if self.score >= 90:
            return 'A'
        elif self.score >= 60:
            return 'B'
        else:
            return 'C'


lisa = Student('Lisa', 99)
# 类的名称
print(Student.__name__)
# 类的文档字符串
print(Student.__doc__)
# 类的属性
print(Student.__dict__)
print(lisa.__dict__)
print(lisa.__dict__.get("name"))
# 类的所有父类构成元素
print(Student.__base__)
