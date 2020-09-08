#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    可视化
"""

__author__ = 'JKong'

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

# 折线图
df = pd.DataFrame(np.random.rand(10, 4), columns=list('ABCD'))
df.plot()
plt.show()
# 条形图
df.plot.bar()
plt.show()

df.plot.bar(stacked=True)
plt.show()

df.plot.barh(stacked=True)
plt.show()

# 直方图
df["A"].plot.hist(bins=40)
plt.show()

# 箱型图
df.plot.box()
plt.show()

# 区域块图形
df.plot.area()
plt.show()

# 散点图
df.plot.scatter(x="A", y="B")
plt.show()

# 饼状图
df["A"].plot.pie(subplots=True)
plt.show()
