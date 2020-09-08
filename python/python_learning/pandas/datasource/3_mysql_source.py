#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    pandas 从MySQL中读取数据
"""

__author__ = 'JKong'

import pandas as pd
from sqlalchemy import create_engine

# 初始化数据库连接，使用pymysql模块
# MySQL的用户：root, 密码:147369, 端口：3306,数据库：test
engine = create_engine('mysql+pymysql://root:123456@localhost:3306/jkong_exec')
# 查询语句，选出employee表中的所有数据
sql = ''' select * from user; '''
# read_sql_query的两个参数: sql语句， 数据库连接
'''
DataFrame.read_sql(sql, con, index_col='None', coerce_float='True', params='None', parse_dates='None', columns='None', chunksize: None = 'None') 
sql: 要执行的SQL查询或表名。
con: 数据源连接
index_col: 设置为索引（多索引）的列。
coerce_float(bool, default True): 尝试将非字符串，非数字对象（例如decimal.Decimal）的值转换为浮点，这对于SQL结果集很有用。
params(list, tuple or dict, optional, default: None): 
parse_dates: 
columns: 要从SQL表中选择的列名列表（仅在读取表时使用）。
chounksize: 如果指定，则返回一个迭代器，其中chunksize是每个块中要包括的行数。
'''
df = pd.read_sql_query(sql, engine)
# 输出employee表的查询结果
print(df)

# 新建pandas中的DataFrame, 只有id,num两列
df = pd.DataFrame({'id': [1, 2, 3, 4],
                   'username': ['zhangsan', 'lisi', 'wangwu', 'zhuliu'],
                   "sex": [1, 2, 1, 2],
                   "birthday": ["2020-09-06", "2020-09-07", "2020-09-08", "2020-09-09"],
                   "address": ["suzhou", "wuxi", "nanjing", "xuzhou"]})
# 将新建的DataFrame储存为MySQL中的数据表，储存index列
'''
DataFrame.to_sql(name, con, schema=None, if_exists='fail', index=True, index_label=None, chunksize=None, dtype=None, method=None)
name: 表名
con: 数据源连接
schema:表结构
    dtype={'EMP_ID': sqlalchemy.types.BigInteger(),
         'GENDER': sqlalchemy.types.String(length=20),
         'AGE': sqlalchemy.types.BigInteger(),
         'EMAIL':  sqlalchemy.types.String(length=50),
         'PHONE_NR':  sqlalchemy.types.String(length=50),
         'EDUCATION':  sqlalchemy.types.String(length=50),
         'MARITAL_STAT':  sqlalchemy.types.String(length=50),
         'NR_OF_CHILDREN': sqlalchemy.types.BigInteger()
                 }
if_exists: 当目标表已经存在时的处理方式，默认是 fail，即目标表存在就失败；
           另外两个选项是 replace 表示替代原表，即删除再创建，append 选项仅添加数据。
index(bool, default True): 将DataFrame索引写为列。使用index_label作为表中的列名。
index_label:
chunksize(int): 指定每个批次中一次要写入的行数。默认情况下，所有行将一次写入。
dtype(dict or scalar, optional):指定列的数据类型。如果使用字典，则键应为列名，值应为SQLAlchemy类型或sqlite3传统模式的字符串。如果提供了标量，它将应用于所有列。
method({None, ‘multi’, callable}, optional):
    Controls the SQL insertion clause used:
    - None : 使用标准的SQL INSERT子句（每行一个）.
    - ‘multi’: 在单个INSERT子句中传递多个值.
    - callable with signature (pd_table, conn, keys, data_iter).
'''
df.to_sql('user', engine, index=False, if_exists="replace")
print('Read from and write to Mysql table successfully!')
