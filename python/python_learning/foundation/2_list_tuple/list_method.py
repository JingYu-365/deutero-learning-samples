#!/usr/bin/env python3
# _*_ coding: utf-8 _*_
# @Author: JKong
# @Time: 2020/7/21 10:25 下午
# @Desc: 列表操作

classmates = ["JKong", "Bob", "Lily"]

# 输出列表中的数据
print(classmates)

# 输出指定下标的数据，下标索引从0开始，最后一个元素的索引为 len(list)-1 ,如果下标有误则会抛出异常。
print(classmates[0])

# 输出列表长度
print(len(classmates))

# 列表中添加元素
classmates.append("LiMing")
print(classmates)

# 在指定下标位置插入指定元素
classmates.insert(1, "Gary")
print(classmates)

# 删除末尾的元素
classmates.pop()
print(classmates)

# 删除自指定下标位置的元素
classmates.pop(1)
print(classmates)

# 对指定下标的数据进行重新赋值
classmates[0] = "JKongZhang"
print(classmates)

# 列表中可以存在不同类型的数据
classmates.append(True)
classmates.insert(0, 0)
print(classmates)
