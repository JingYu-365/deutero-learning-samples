#!/usr/bin/env python3
# -*- coding: utf-8 -*-

""" 
    学生类
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
bart = Student('Bart', 59)
print(lisa.name, lisa.get_grade())
print(bart.name, bart.get_grade())
print(Student.country)
