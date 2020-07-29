#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    Python内置的@property装饰器就是负责把一个方法变成属性调用的
"""

__author__ = 'JKong'


class Student(object):

    @property
    def score(self):
        return self._score

