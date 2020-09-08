#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    通过 pandas 操作 Presto
"""

__author__ = 'JKong'

from sqlalchemy import *
from sqlalchemy.engine import create_engine
from sqlalchemy.schema import *

# todo 在连接时，必须要在指定什么catalog，指定什么database
engine = create_engine('presto://10.10.32.17:8081/hive/jkong_big_data')
logs = Table('user_1000', MetaData(bind=engine), autoload=True)
print(select([func.count('*')], from_obj=logs).scalar())

# todo 切记：此处SQL结尾不可以添加';'
sql = ''' select * from hive.jkong_big_data.user_1000 where age = 10 '''
# 此处不使用 hive.jkong_big_data也可以实现
# sql = ''' select * from user_1000 where age = 10 '''


# 执行原生sql 方式一：
ret = engine.execute(sql)
# print(dir(engine))
# print(ret.fetchone())
print(ret.fetchall())

# 执行原生sql 方式二：
# conn = engine.connect()
# res = conn.execute(sql)
# all_res_list = res.fetchall()
# print(all_res_list)
