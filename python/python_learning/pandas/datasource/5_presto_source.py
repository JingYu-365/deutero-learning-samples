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
engine = create_engine('presto://10.10.27.47:38288/hive/sas_db')
logs = Table('train_data_set', MetaData(bind=engine), autoload=True)
print(select([func.count('*')], from_obj=logs).scalar())

# todo 切记：此处SQL结尾不可以添加';'
sql = ''' select * from hive.sas_db.train_data_set '''
# 此处不使用 hive.jkong_big_data也可以实现
# sql = ''' select * from user_1000 where age = 10 '''


# 执行原生sql 方式一：
ret = engine.execute(sql)
# print(dir(engine))
# print(ret.fetchone())
print(ret.fetchall())

print(engine.execute("desc hive.sas_db.train_data_set").fetchall())

# 执行原生sql 方式二：
# conn = engine.connect()
# res = conn.execute(sql)
# all_res_list = res.fetchall()
# print(all_res_list)
