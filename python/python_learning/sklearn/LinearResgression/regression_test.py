#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    线性回归测试
    todo：待调试
"""

__author__ = 'JKong'

from sklearn.datasets import load_boston
from sklearn.linear_model import LinearRegression, SGDRegressor, Ridge
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler


def linearmodel():
    """
    线性回归对波士顿数据集处理
    :return: None
    """

    # 1、加载数据集
    ld = load_boston()
    x_train, x_test, y_train, y_test = train_test_split(ld.data, ld.target, test_size=0.25)

    # 2、标准化处理
    # 特征值处理
    std_x = StandardScaler()
    x_train = std_x.fit_transform(x_train)
    x_test = std_x.transform(x_test)
    # 目标值进行处理
    std_y = StandardScaler()
    # y_train = std_y.fit_transform(y_train)
    # y_test = std_y.transform(y_test)

    # 3、估计器流程
    # LinearRegression
    lr = LinearRegression()
    lr.fit(x_train, y_train)
    print(lr.coef_)
    y_lr_predict = lr.predict(x_test)
    y_lr_predict = std_y.inverse_transform(y_lr_predict)
    print("Lr预测值：", y_lr_predict)

    # SGDRegressor
    sgd = SGDRegressor()
    sgd.fit(x_train, y_train)
    # print(sgd.coef_)
    y_sgd_predict = sgd.predict(x_test)
    y_sgd_predict = std_y.inverse_transform(y_sgd_predict)
    print("SGD预测值：", y_sgd_predict)

    # 带有正则化的岭回归
    rd = Ridge(alpha=0.01)
    rd.fit(x_train, y_train)
    y_rd_predict = rd.predict(x_test)
    y_rd_predict = std_y.inverse_transform(y_rd_predict)
    print(rd.coef_)

    # 两种模型评估结果
    print("lr的均方误差为：", mean_squared_error(std_y.inverse_transform(y_test), y_lr_predict))
    print("SGD的均方误差为：", mean_squared_error(std_y.inverse_transform(y_test), y_sgd_predict))
    print("Ridge的均方误差为：", mean_squared_error(std_y.inverse_transform(y_test), y_rd_predict))
    return None


linearmodel()
