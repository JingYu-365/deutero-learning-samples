#!/usr/bin/env python3
# -*- coding: utf-8 -*-

""" 
    读取文件内容
"""

__author__ = 'JKong'

import io

try:
    file = open(
        "3_read_file.py", "r")
    print(file.read())
finally:
    if file:
        file.close()
