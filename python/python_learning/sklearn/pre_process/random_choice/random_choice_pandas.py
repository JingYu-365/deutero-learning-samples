#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    使用pandas实现随机采样
"""

__author__ = 'JKong'

import pandas as pd
import numpy as np

# 100行6列
df = pd.DataFrame(np.arange(600).reshape(100, 6), columns=['A', 'B', 'C', 'D', 'E', 'F'])
print(df)
#
# # 设置随机种子，若不设置随机种子，则每次抽样的结果都不一样
# np.random.seed(10)
# # 按个数抽样，不放回，抽取10个数据，这10个数据肯定不一样，因为是不放回抽样
# df = df.sample(n=10)
# print(df)
#
# # 按个数抽样，有放回抽样，抽取20个数据，这20个数据里可能有一样的数据，因为是放回抽样
# df.sample(n=20, replace=True)

# 按照百分比抽样，不放回，抽取20%的数据
# df.sample(frac=0.2)
# 按照百分比抽样，有放回，抽取20%的数据

face_replace = df.sample(frac=0.1, replace=True, random_state=np.random.seed(10))
print(face_replace)

# DataFrame.sample(n=None, frac=None, replace=False, weights=None, random_state=None, axis=None)
# n：抽取的行数。（例如n=20000时，抽取其中的2000行）不可以与face同时设置。
#
# frac：是抽取的比列。（不关系行数，抽取其中的百分比，可以选择使用frac，例如：frac=0.8，就是抽取其中80%）
#
# replace：是否为有放回抽样，取replace=True时为有放回抽样。
#
# weights：每个样本的权重。
#
# random_state：设置随机种子，若不设置随机种子，则每次抽样的结果都不一样
#
# axis：是选择抽取数据的行还是列。axis=0的时是抽取行，axis=1时是抽取列（也就是说axis=1时，在列中随机抽取n列，在axis=0时，在行中随机抽取n行）
