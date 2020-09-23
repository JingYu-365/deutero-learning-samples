#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    TODO
"""

__author__ = 'JKong'


class ShortInputException(Exception):
    '''自定义异常类'''

    def __init__(self, length, atleast):
        self.length = length
        self.atleast = atleast


try:
    s = input('please input:')
    if len(s) < 3:
        raise ShortInputException(len(s), 3)
except ShortInputException as e:
    print('输入长度是%s,长度至少是%s' % (e.length, e.atleast))
else:
    print('nothing...')
