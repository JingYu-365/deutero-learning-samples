#!/usr/bin/env python3
# -*- coding: utf-8 -*-

""" 
    测试 get set 方法
"""

__author__ = 'JKong'


class Student2(object):
    def __init__(self, name, score):
        self.__name = name
        self.__score = score

    def get_name(self):
        return self.__name

    def get_score(self):
        return self.__score

    def set_name(self, name):
        if name == "":
            raise ValueError('bad name');
        self.__name = name

    def set_score(self, score):
        if 0 <= score <= 100:
            self.__score = score
        else:
            raise ValueError('bad score')
