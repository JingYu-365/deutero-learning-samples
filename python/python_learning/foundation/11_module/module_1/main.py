#!/usr/bin/env python3
# -*- coding: utf-8 -*-

""" 
    包导入测试
"""

__author__ = 'JKong'

# import test
#
# print(test.test_add(2, 3))


# import study.test2 as test2
# from study import test2
# print(test2.test_remove(5, 2))

# from study.test2 import test_remove
# print(test_remove(10, 5))


import sys

sys.path.append("../")

import msg.send as msg

msg.send_msg('JKong', "hello!")
