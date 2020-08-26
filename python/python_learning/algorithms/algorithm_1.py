#!/usr/bin/env python3
# -*- coding: utf-8 -*-

""" 
    collections 中 数据结构学习
"""

__author__ = 'JKong'

# 6. 字典中一个key映射多个值
from collections import defaultdict

# value 结构为 list
d = defaultdict(list)
d["a"].append(1)
d["a"].append(2)
d["a"].append(3)
d["a"].append(4)
print(d)

# value 结构为 set
s = defaultdict(set)
s["b"].add(10)
s["b"].add(11)
s["b"].add(12)
s["b"].add(13)
print(s)

# 7. 字典顺序问题
dic_1 = {"a": 1, "b": 2}
dic_2 = {"b": 2, "a": 1}
print(dic_1 == dic_2)  # true

from collections import OrderedDict

dic_1 = OrderedDict()
dic_1["a"] = 1
dic_1["b"] = 2

dic_2 = OrderedDict()
dic_2["b"] = 2
dic_2["a"] = 1
print(dic_1 == dic_2)  # false

# 8. 字典 最大值 最小值 排序
dic_3 = {"key3": 3, "key5": 1, "key2": 2}
# 最小值
value_min = min(zip(dic_3.values(), dic_3.keys()))  # 会使用第一个元素进行排序比较，所以需要将value放在前面
print(value_min)

value_min = min(dic_3.items(), key=lambda x: x[1])  # key 指定用来比较的字段
print(value_min)

# 最大值
value_max = max(zip(dic_3.values(), dic_3.keys()))
print(value_max)

value_max = max(dic_3.items(), key=lambda x: x[1])
print(value_max)

# 元素排序
sorted_value = sorted(zip(dic_3.values(), dic_3.keys()))
print(sorted_value)

sorted_value = sorted(dic_3.items(), key=lambda x: x[1])
print(sorted_value)

# 9. 字典与字典操作
dic_4 = {"a": 1, "b": 2, "c": 3}
dic_5 = {"b": 2, "c": 3, "d": 4}
# 字典的并集
print(dic_4.keys() | dic_5.keys())
# 字典的交集
print(dic_4.keys() & dic_5.keys())
# 字典的差集
print(dic_4.keys() - dic_5.keys())


# 10. 删除相同元素并保证原有顺序
# 定义一个去重函数，实现list元素去重
def remove_repeat(items):
    tmp_set = set()
    for item in items:
        if item not in tmp_set:
            # yield 同于 return，但是不会中断函数，函数人会执行，只是会将元素返回
            yield item
            tmp_set.add(item)


list_tmp = [1, 2, 3, 3, 4, 4, 5]
print(list(remove_repeat(list_tmp)))

# 定义一个函数，实现元组去重
# todo

# 11. 命名切片
str = "this is a dog";
slice_1 = slice(3, 8)
result = str[slice_1]
print(result)

# 12. 序列中出现次数最多的 Top N
from collections import Counter

a = ["A", "A", "b", "c", "c", "d", "c", "f", "c", "d"]
word_count = Counter(a)
print(word_count)
print("top 3: ", word_count.most_common(3))
# 更新统计元素
word_new = ["a", "b", "a", "b", "A"]
word_count.update(word_new)
print(word_count)

# 13. 通过关键字排序某个字典
dic_6 = [{"key1": 11, "key3": 13, "key2": 12}, {"key1": 1, "key3": 13, "key2": 12}, {"key1": 1, "key3": 3, "key2": 2}]

dic_sorted = sorted(dic_6, key=lambda x: x["key1"])
print(dic_sorted)
dic_sorted = sorted(dic_6, key=lambda x: (x["key1"], x["key2"]))  # 先根据 key1排序，如果 key1 相同，再按照 key2 排序
print(dic_sorted)

from operator import itemgetter

dic_sorted = sorted(dic_6, key=itemgetter("key1"))
print(dic_sorted)
dic_sorted = sorted(dic_6, key=itemgetter("key1", "key2"))  # 先根据 key1排序，如果 key1 相同，再按照 key2 排序
print(dic_sorted)


# 14. 对象根据某一属性进行排序
class User:
    def __init__(self, user_id):
        self.user_id = user_id

    def __repr__(self):
        return "User({})".format(self.user_id)


users = [User(2), User(5), User(3)]
users_sorted = sorted(users, key=lambda x: x.user_id)
print(users_sorted)

from operator import attrgetter

users_sorted = sorted(users, key=attrgetter("user_id"))
print(users_sorted)

# 15. 通过关键字段进行分组
from itertools import groupby

user_logs = [
    {"user": 1, "age": 12, "date": "2012/12"},
    {"user": 2, "age": 13, "date": "2012/12"},
    {"user": 3, "age": 14, "date": "2012/12"},
    {"user": 4, "age": 14, "date": "2012/12"},
    {"user": 5, "age": 12, "date": "2012/12"},
]
# 此分组需要先对数据根据分组字段进行排序
user_logs = sorted(user_logs, key=lambda x: x["age"])
for age, items in groupby(user_logs, key=lambda x: x["age"]):
    print("age:", age)
    for item in items:
        print(item)

# 16. 过滤序列元素
list_1 = [1, 2, 3, 4, 5, 6, 7, 8, 9]
list_1 = [n for n in list_1 if n % 2 == 0]
print(list_1)


# 通过函数filter方式实现过滤
def is_even(val):
    if val % 2 == 0:
        return True
    else:
        return False


list_1 = list(filter(is_even, list_1))
print(list_1)
