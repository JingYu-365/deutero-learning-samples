#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    pandas 操作 Hive 获取数据
    @see: https://www.cnblogs.com/traditional/p/12534719.html
"""

__author__ = 'JKong'

'''
安装环境：
    pip install sasl
    pip install thrift
    pip install thrift-sasl
    pip install PyHive


此方式在window环境中会抛出此问题：
thrift.transport.TTransport.TTransportException: Could not start SASL: b'Error in sasl_client_start (-4) SASL(-4): no mechanism available: Unable to find a callback: 2'
'''
# from pyhive import hive
#
# conn = hive.Connection(host='10.10.32.17', port=10000, username='hdfs', database='default', auth="NOSASL")
# cursor = conn.cursor()
# cursor.execute('show tables')
#
# for result in cursor.fetchall():
#     print(result)

'''
安装依赖
    pip install bit_array
    pip install thrift
    pip install thriftpy
    pip install pure_sasl
    pip install --no-deps thrift-sasl==0.2.1
    pip install impyla

'''
from impala.dbapi import connect

conn = connect(host='10.10.32.17',
               port=10000,
               database='jkong_big_data',
               user='hdfs',
               auth_mechanism="PLAIN")

cur = conn.cursor()
cur.execute('select * from user_1000 where age = 10')
print(cur.fetchall())
