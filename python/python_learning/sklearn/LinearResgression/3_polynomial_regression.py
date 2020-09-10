#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    多项式回归
    - 在一元回归分析中，如果依变量y与自变量x的关系为非线性的，但是又找不到适当的函数曲线来拟合，则可以采用一元多项式回归。
    - 多项式回归的最大优点就是可以通过增加x的高次项对实测点进行逼近，直至满意为止。
    - 事实上，多项式回归可以处理相当一类非线性问题，它在回归分析中占有重要的地位，因为任一函数都可以分段用多项式来逼近。

    二次回归（Quadratic Regression），即回归方程有个二次项，公式如下：
            y=α+β1x+β2x2
    只用一个解释变量，但是模型有三项，通过第三项（二次项）来实现曲线关系。
    PolynomialFeatures转换器可以用来解决这个问题。代码如下：
    PolynomialFeatures转换器的作用就是将[x1,x2]默认转换为[1,x1,x2,x21,x1x2,x22]
"""

__author__ = 'JKong'

import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import PolynomialFeatures

plt.rcParams['font.sans-serif'] = ['SimHei']  # 用来正常显示中文标签


def runplt(size=None):
    plt.figure(figsize=size)
    plt.title('匹萨价格与直径数据')
    plt.xlabel('直径（英寸）')
    plt.ylabel('价格（美元）')
    plt.axis([0, 25, 0, 25])
    plt.grid(True)
    return plt


X_train = [[6], [8], [10], [14], [18]]
y_train = [[7], [9], [13], [17.5], [18]]
X_test = [[6], [8], [11], [16]]
y_test = [[8], [12], [15], [18]]
regressor = LinearRegression()
regressor.fit(X_train, y_train)
xx = np.linspace(0, 26, 100)
yy = regressor.predict(xx.reshape(xx.shape[0], 1))
plt = runplt(size=(8, 8))
plt.plot(X_train, y_train, 'k.', label="train")
plt.plot(xx, yy, label="一元线性回归")

# 多项式回归
quadratic_featurizer = PolynomialFeatures(degree=2)
X_train_quadratic = quadratic_featurizer.fit_transform(X_train)
X_test_quadratic = quadratic_featurizer.transform(X_test)
regressor_quadratic = LinearRegression()

# 训练数据集用来fit拟合
regressor_quadratic.fit(X_train_quadratic, y_train)
xx_quadratic = quadratic_featurizer.transform(xx.reshape(xx.shape[0], 1))
# 测试数据集用来predict预测
plt.plot(xx, regressor_quadratic.predict(xx_quadratic), 'r-', label="多项式回归")
plt.legend()
plt.show()
print(X_train)
print(X_train_quadratic)
print(X_test)
print(X_test_quadratic)
print('一元线性回归 r-squared', regressor.score(X_test, y_test))
print('二次回归 r-squared', regressor_quadratic.score(X_test_quadratic, y_test))
