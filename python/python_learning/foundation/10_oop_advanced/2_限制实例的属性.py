#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    Python允许在定义class的时候，定义一个特殊的__slots__变量，来限制该class实例能添加的属性
【注意】
    使用__slots__要注意，__slots__定义的属性仅对当前类实例起作用，对继承的子类是不起作用的
"""

__author__ = 'JKong'


class Student(object):
    # 用tuple定义允许绑定的属性名称
    __slots__ = ('name', 'age')


class GraduateStudent(Student):
    pass


s = Student()  # 创建新的实例
s.name = 'JKong'  # 绑定属性'name'
s.age = 25  # 绑定属性'age'
# ERROR: AttributeError: 'Student' object has no attribute 'score'
try:
    s.score = 99
except AttributeError as e:
    print('AttributeError:', e)

g = GraduateStudent()
g.score = 99
print('g.score =', g.score)
