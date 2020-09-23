#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    装饰器
"""

__author__ = 'JKong'


# 使用 装饰器进行函数增强 （面向切面编程）
def log(func):
    def wrapper(*args, **kw):
        print("前置")
        print('call %s():' % func.__name__)
        ret = func(*args, **kw)
        print("后置")
        return ret

    return wrapper


# 使用 @ 语法将 log 函数增强到 now 函数中，也就相当于：log(now)
# 由于log()是一个decorator，返回一个函数，所以，原来的now()函数仍然存在，
# 只是现在同名的now变量指向了新的函数，于是调用now()将执行新函数，即在log()函数中返回的wrapper()函数。
@log
def now():
    print("2020-7-23")


# 调用函数
now()

print("==================")


# 如果decorator本身需要传入参数，那就需要编写一个返回decorator的高阶函数，写出来会更复杂。
def log1(text):
    def decorator(func):
        def wrapper(*args, **kw):
            print('%s %s():' % (text, func.__name__))
            return func(*args, **kw)

        return wrapper

    return decorator


# 那么也就相当于：now = log('execute')(now)
@log1("execute")
def now1():
    print("2020-7-23")


now1()

print("=================")

# 获取函数的名称
print(log1.__name__)  # log1
print(now1.__name__)  # wrapper

# 因为返回的那个wrapper()函数名字就是'wrapper'，所以，需要把原始函数的__name__等属性复制到wrapper()函数中，
# 否则，有些依赖函数签名的代码执行就会出错。
# 不需要编写wrapper.__name__ = func.__name__这样的代码，Python内置的functools.wraps就是干这个事的
import functools


def log2(text):
    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args, **kw):
            print('%s %s():' % (text, func.__name__))
            return func(*args, **kw)

        return wrapper

    return decorator


@log2("execute")
def now2():
    print("2020-7-23")


print(now2.__name__)
