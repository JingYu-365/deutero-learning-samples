#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Author: JKong
# @Time: 2020/7/23 11:32 上午
# @Desc: 高阶函数英文叫Higher-order function。

# 变量可以指向函数
a = abs  # 如果写成 a = abs() 是不正确的
print(a(-1))

# 变量可以指向函数，本质上是函数名也是变量。
# 对于abs()这个函数，完全可以把函数名abs看成变量，它指向一个可以计算绝对值的函数！

# 也可以把abs指向其他对象
print(abs)
abs = "JKong"
print(abs)


# 既然函数名是一个变量，那么在实际使用时也可以做为函数的参数进行传递
def add(x, y, f):
    return f(x) + f(y)


print(add(123, -123, a))
