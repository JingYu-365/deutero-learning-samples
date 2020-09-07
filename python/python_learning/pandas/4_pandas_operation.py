#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    pandas 数据操作
    1. 缺失数据
    2. 分组（groupBy）
    3. 合并/连接
"""

__author__ = 'JKong'

import numpy as np
import pandas as pd

# 数据缺失
print("================== 数据缺失处理 ==================")
d = {'one': pd.Series([1, np.nan, 3], index=['a', 'b', 'c']),
     'two': pd.Series([1, 2, 3, 4], index=['a', 'b', 'c', 'd'])}
df = pd.DataFrame(d)
print("data: \n", df)

# 检测数据缺失
print("\none isnull:\n", df["one"].isnull)
print("\ndf isnull:\n", df.isnull)

# 缺少数据的计算，求和时Na被视为0
print("\none sum: ", df["one"].sum())

# 清理/填充缺少数据
print("\n填充0，结果：\n", df.fillna(0))
print("\n填充平均值，结果：\n", df["one"].fillna(df["one"].mean()))
# pad 向前填充，bfill 向后填充
print("\n填充前值，结果：\n", df.fillna(method="pad"))
print("\n填充后值，结果：\n", df.fillna(method="bfill"))

# 丢弃缺少的值
print("\n原数据: \n", df)
print("\n丢弃空值后: \n", df.dropna())

print("================== 数据分组 ==================")


# 查看分组内容
def show_group(groups):
    for name, group in groups:
        print("______", name)
        print(group)


ipl_data = {'Team': ['Riders', 'Riders', 'Devils', 'Devils', 'Kings',
                     'kings', 'Kings', 'Kings', 'Riders', 'Royals', 'Royals', 'Riders'],
            'Rank': [1, 2, 2, 3, 3, 4, 1, 1, 2, 4, 1, 2],
            'Year': [2014, 2015, 2014, 2015, 2014, 2015, 2016, 2017, 2016, 2014, 2015, 2017],
            'Points': [876, 789, 863, 673, 741, 812, 756, 788, 694, 701, 804, 690]}
df = pd.DataFrame(ipl_data)
print("\n分组数据:\n", df)

# 将数据拆分成组
print("\n按照Team分组：\n", df.groupby("Team").groups)
show_group(df.groupby(["Team"]))
print("\n按照 Team Year 分组：\n", df.groupby(["Team", "Year"]).groups)
# 迭代遍历分组
print()
grouped = df.groupby("Year")
show_group(grouped)

# 聚合
print("\n聚合操作（sum | mean | std）:\n", grouped["Points"].agg([np.sum, np.mean, np.std]))

# 过滤
print("\n数据过滤（分组内数据量超过3个元素的：）：\n", df.groupby("Team").filter(lambda x: len(x) >= 3))

print("================== 合并/连接 ==================")
left = pd.DataFrame({
    'id': [1, 2, 3, 4, 5],
    'Name': ['Alex', 'Amy', 'Allen', 'Alice', 'Ayoung'],
    'subject_id': ['sub1', 'sub2', 'sub4', 'sub6', 'sub5']})
right = pd.DataFrame(
    {'id': [1, 2, 3, 4, 5],
     'Name': ['Billy', 'Brian', 'Bran', 'Bryce', 'Betty'],
     'subject_id': ['sub2', 'sub4', 'sub3', 'sub6', 'sub5']})
rs = pd.merge(left, right, on="id")
print("\n通过id连接：\n", rs)

rs = pd.merge(left, right, on=["id", "subject_id"])
print("\n通过id & subject_id连接：\n", rs)

# left join
rs = pd.merge(left, right, on="subject_id", how="left")
print("\n通过 subject_id 左连接：\n", rs)
# right join
rs = pd.merge(left, right, on="subject_id", how="right")
print("\n通过 subject_id 右连接：\n", rs)
# inner join
rs = pd.merge(left, right, on="subject_id", how="inner")
print("\n通过 subject_id 内连接：\n", rs)
# outer join
rs = pd.merge(left, right, on="subject_id", how="outer")
print("\n通过 subject_id 外连接：\n", rs)
