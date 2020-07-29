#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    为对象动态添加能力
"""

__author__ = 'JKong'


class Student(object):
    pass


# 给实例绑定一个属性
stu1 = Student()
stu1.name = "JKong"
print(stu1.name)


# 给实例绑定一个方法
def set_age(self, age):
    self.age = age


import types as types

stu1.set_age = types.MethodType(set_age, stu1)
stu1.set_age(25)
print(stu1.age)

# 给一个实例绑定的方法，对另一个实例是不起作用的
stu2 = Student()


# stu2.set_age(26)
# print(stu2.age)


# 给类绑定方法
def set_score(self, score):
    self.score = score


Student.set_score = set_score
stu3 = Student()
stu3.set_score(99)
print(stu3.score)
