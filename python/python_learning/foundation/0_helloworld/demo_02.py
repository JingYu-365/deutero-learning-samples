#!/usr/bin/env python3
# _*_ coding: utf-8 _*_
# @Author: JKong
# @Time: 2020/7/21 9:17 下午
# @Desc: python 运算算符

'''
    算术运算符
'''
print("算术运算符")
num1 = 10
num2 = 3
print(num1 + num2)
print(num1 - num2)
print(num1 * num2)
# 除法
print(num1 / num2)
# 除法取整
print(num1 // num2)
print(num1 % num2)
# 幂次方
print(num1 ** num2)

'''
    比较运算符
'''
print("\n比较运算符")
print(num1 == num2)
print(num1 != num2)
print(num1 >= num2)
print(num1 <= num2)
print(num1 > num2)
print(num1 < num2)

'''
    逻辑运算符
'''
print("\n逻辑运算符")
print(True and False)
print(True or False)
print(not False)
# 空字符串 | 空字典 | 空数组 | 空元组 | 0 返回是False
print(bool(""))
print(bool({}))
print(bool([]))
print(bool(()))
print(bool(0))
