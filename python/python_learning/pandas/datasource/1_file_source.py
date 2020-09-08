#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    通过pandas读取文件并进行操作
    - txt
    - csv
    - excel
    - json

    其他：
    - DataFrame 转为 json
"""

__author__ = 'JKong'

import pandas as pd

# 读取txt文件中的数据
df = pd.read_csv(r"./data/user_info.txt",
                 sep=',', header=None, names=['id', 'name', 'age', 'gender', 'time'])
print("\n从 txt 文件读取数据：\n", df)

# 读取txt文件中的数据
df = pd.read_csv(r"./data/user_info.csv",
                 sep=',', header=None, names=['id', 'name', 'age', 'gender', 'time'])
print("\n从 txt 文件读取数据：\n", df)

# 读取Excel文件中的数据
df = pd.read_excel(r"./data/ser_info.xlsx")
print("\n从 Excel 文件读取数据：\n", df)

# 读取 json 文件中的数据
df = pd.read_json(r"./data/user_info.json")
print("\n从 json 文件读取数据：\n", df)

'''
The format of the JSON string:
    ‘split’ : dict like {‘index’ -> [index], ‘columns’ -> [columns], ‘data’ -> [values]}
    ‘records’ : list like [{column -> value}, … , {column -> value}]
    ‘index’ : dict like {index -> {column -> value}}
    ‘columns’ : dict like {column -> {index -> value}}
    ‘values’ : just the values array
    ‘table’ : dict like {‘schema’: {schema}, ‘data’: {data}}
'''
print(df.to_json(orient='records'))
