#!/usr/bin/env python3
# -*- coding: utf-8 -*-

""" 
    动物类
"""

__author__ = 'JKong'


class Animal(object):

    def __init__(self, name):
        self.__name = name


class Dog(Animal):
    pass


class Cat(Animal):
    pass
