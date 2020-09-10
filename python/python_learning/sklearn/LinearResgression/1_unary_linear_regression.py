#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    一元线性回归假设解释变量和响应变量之间存在线性关系；
        在一元线性回归中，一个维度是响应变量，另一个维度是解释变量，总共两维。因此，其超平面只有一维，就是一条线。
        LinearRegression类的fit()方法学习下面的一元线性回归模型：
                                y=α+βx
        y表示响应变量的预测值，本例指匹萨价格预测值，x是解释变量。
        截距α和系数β是线性回归模型最关心的事情。
"""

__author__ = 'JKong'

import matplotlib.pyplot as plt
from matplotlib.font_manager import FontProperties


def runplt(size=None):
    plt.figure(figsize=size)
    plt.title('Pizza price and diameter data')
    plt.xlabel('Diameter (inch)')
    plt.ylabel('Price (USD)')
    plt.axis([0, 25, 0, 25])
    plt.grid(True)
    return plt


plt = runplt()
# 披萨的尺寸
X = [[6], [8], [10], [14], [18]]
# 披萨的价格
y = [[7], [9], [13], [17.5], [18]]
plt.plot(X, y, 'k.')
# 展示
plt.show()

# 调用sklearn中的linear_model模块进行线性回归。
from sklearn import linear_model
import numpy as np

'''
linear_model.LinearRegression() 参数：
- fit_intercept : 布尔型参数，表示是否计算该模型截距。可选参数。
- normalize : 布尔型参数，若为True，则X在回归前进行归一化。可选参数。默认值为False。
- copy_X : 布尔型参数，若为True，则X将被复制；否则将被覆盖。 可选参数。默认值为True。
- n_jobs : 整型参数，表示用于计算的作业数量；若为-1，则用所有的CPU。可选参数。默认值为1。
'''
model = linear_model.LinearRegression()
'''
线性回归fit函数用于拟合输入输出数据，调用形式为model.fit(X,y, sample_weight=None)
- X : X为训练向量；
- y : y为相对于X的目标向量；
- sample_weight : 分配给各个样本的权重数组，一般不需要使用，可省略。
注意：X，y 以及model.fit()返回的值都是2-D数组，如：a= [[0]]
'''
model.fit(X, y)
print(model.intercept_)  # 截距
print(model.coef_)  # 线性模型的系数
a = model.predict([[12]])
print(a[0][0])
# a[0][0]
print("预测一张12英寸匹萨价格：{:.2f}".format(model.predict([[12]])[0][0]))

'''
【说明】
sklearn.linear_model.LinearRegression类是一个估计器（estimator）,估计器依据观测值来预测结果。
在scikit-learn里面，所有的估计器都带有 fit() 和 predict() 方法。
- fit()用来分析模型参数，
- predict()是通过 fit() 算出的模型参数构成的模型，对解释变量进行预测获得的值。
'''

# 测试预测模型
plt = runplt()
plt.plot(X, y, 'k.')
model = linear_model.LinearRegression()
model.fit(X, y)

X2 = [[0], [10], [14], [25]]
y2 = model.predict(X2)
plt.plot(X2, y2, 'g-')
plt.show()
