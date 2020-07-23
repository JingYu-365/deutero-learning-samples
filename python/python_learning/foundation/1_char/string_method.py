#!/usr/bin/env python3
# _*_ coding: utf-8 _*_
# @Author: JKong
# @Time: 2020/7/21 9:56 下午
# @Desc: string 常用方法

# find 不存在返回-1，存在则返回下标开始位置
str = "hello"
print(str.find("ll"))

# index 存在返回下标开始位置，否则报错
print(str.index("ll"))

# count 统计字符串字符出现的次数
print(str.count("l"))
print(str.count("l", 3, len(str)))

# replace 将字符替换为某字符
print(str.replace("h", "H"))

# split 根据某个字符进行分割字符串
str = 'hello world'
print(str.split(" "))

# capitalize 将字符串首字母大写
print(str.capitalize())

# title 将字符串中每个单词大写
print(str.title())

# startWith 验证是否以某个字符或字符串开头
print(str.startswith("hell"))
print(str.startswith("ell"))

# endWIth 验证是否以某个字符或字符串结束
print(str.endswith("hell"))
print(str.endswith("orld"))

str = "I Love You"
# lower 将字符串转为全小写
print(str.lower())

# upper 将字符串转为全大写
print(str.upper())

# ljust rjust center 左对齐，右对齐，居中
print(str.ljust(20))
print(str.rjust(20))
print(str.center(20))

# lstrip rstrip strip 去除左边空格 去除右边空格 去除两边空格
print(str.center(20).lstrip())
print(str.center(20).rstrip())
print(str.center(20).strip())

# partition 根据字符分割成几个字符串
print(str.partition("Love"))

# join 将字符串数组元素使用指定字符拼接在一起
c = '_'
arr = ["asd", '123', "dsa"]
print(c.join(arr))

print("==========")
str = "123Hll"
# isspace 判断是否存在空格
print(str.isspace())

# isalnum 判断是否全数字和字母
print(str.isalnum())

# isdigit 判断是否全数字
print(str.isdigit())

# isalpha 判断是否全字母
print(str.isalpha())
