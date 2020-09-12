#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    多项式回归
    - 在一元回归分析中，如果依变量y与自变量x的关系为非线性的，但是又找不到适当的函数曲线来拟合，则可以采用一元多项式回归。
    - 多项式回归的最大优点就是可以通过增加x的高次项对实测点进行逼近，直至满意为止。
    - 事实上，多项式回归可以处理相当一类非线性问题，它在回归分析中占有重要的地位，因为任一函数都可以分段用多项式来逼近。

    多项式回归：若希望回归模型更好的拟合训练样本数据，可以使用多项式回归器。
    一元多项式回归：
        数学模型：y = w0 + w1 * x^1 + w2 * x^2 + .... + wn * x^n
        将高次项看做对一次项特征的扩展得到：
                y = w0 + w1 * x1 + w2 * x2 + .... + wn * xn
        那么一元多项式回归即可以看做为多元线性回归，可以使用LinearRegression模型对样本数据进行模型训练。

        所以一元多项式回归的实现需要两个步骤：
            1. 将一元多项式回归问题转换为多元线性回归问题（只需给出多项式最高次数即可）。
            2. 将1步骤得到多项式的结果中 w1,w2,w3,...,wn当做样本特征，交给线性回归器训练多元线性模型。

    选择合适的最高次数其模型R2评分会高于一元线性回归模型评分，如果次数过高，会出现过拟合现象，评分会低于一元线性回归评分

        使用sklearn提供的"数据管线"实现两个步骤的顺序执行：
            import sklearn.pipeline as pl
            import sklearn.preprocessing as sp
            import sklearn.linear_model as lm

            model = pl.make_pipeline(
                # 10: 多项式的最高次数
                sp.PolynomialFeatures(10),  # 多项式特征扩展器
                lm.LinearRegression())      # 线性回归器

    过拟合和欠拟合：
        1.过拟合：过于复杂的模型，对于训练数据可以得到较高的预测精度，但对于测试数据通常精度较低，这种现象叫做过拟合。
        2.欠拟合：过于简单的模型，无论对于训练数据还是测试数据都无法给出足够高的预测精度，这种现象叫做欠拟合。
        3.一个性能可以接受的学习模型应该对训练数据和测试数据都有接近的预测精度，而且精度不能太低。
                训练集R2       测试集R2
                    0.3        0.4    欠拟合：过于简单，无法反映数据的规则
                    0.9        0.2    过拟合：过于复杂，太特殊，缺乏一般性
                    0.7        0.6    可接受：复杂度适中，既反映数据的规则，同时又不失一般性

    加载single.txt文件中的数据，基于一元多项式回归算法训练回归模型。
        步骤：
            导包--->读取数据--->创建多项式回归模型--->模型训练及预测--->通过模型预测得到pred_y，绘制多项式函数图像
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
