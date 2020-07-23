#!/usr/bin/env python3
# _*_ coding: utf-8 _*_
# @Author: JKong
# @Time: 2020/7/23 9:58 上午
# @Desc: 元组相关方法操作
# 当你定义一个tuple时，在定义的时候，tuple的元素就必须被确定下来

classmates = ("JKong", "Bob", "Lily")
print(classmates)

# 定义一个空tuple
classmates_empty = ()
print(classmates_empty)

# 定义只有一个元素的tuple为了防止歧义，会添加一个","
classmates_single = (1,)
print(classmates_single)

# 不同数据类型的数据
classmates_list = ["JKong", "Bob", "Lily"]
classmates_dif = (1, True, "Zhang", classmates_list)
print(classmates_dif)
classmates_list.append("XiaoMing")
print(classmates_dif)
