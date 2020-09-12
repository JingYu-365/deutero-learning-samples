#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    回归模型评估指标
"""

__author__ = 'JKong'

from sklearn import datasets
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error
from sklearn.metrics import median_absolute_error
from sklearn.metrics import mean_squared_log_error
from sklearn.metrics import mean_absolute_error
from sklearn.metrics import explained_variance_score
from sklearn.metrics import r2_score

import numpy as np

bos_house = datasets.load_boston()
bos_house_data = bos_house['data']
bos_house_target = bos_house['target']

x_train, x_test, y_train, y_test = train_test_split(bos_house_data, bos_house_target, random_state=41)
forest_reg = RandomForestRegressor(random_state=41)
forest_reg.fit(x_train, y_train)
y_pred = forest_reg.predict(x_test)

# mean_squared_error (均方差（Mean squared error，MSE），该指标计算的是拟合数据和原始数据对应样本点的误差的平方和的均值，其值越小说明拟合效果越好。)
print('MSE为：', mean_squared_error(y_test, y_pred))
print('MSE为(直接计算)：', np.mean((y_test - y_pred) ** 2))

# 均方根误差RMSE(root-mean-square error)， 均方根误差亦称标准误差,它是观测值与真值偏差的平方与观测次数比值的平方根。均方根误差是用来衡量观测值同真值之间的偏差。
print('RMSE为：', np.sqrt(mean_squared_error(y_test, y_pred)))

# median_absolute_error (平均绝对误差（Mean Absolute Error，MAE），用于评估预测结果和真实数据集的接近程度的程度，其值越小说明拟合效果越好。)
print(np.median(np.abs(y_test - y_pred)))
print(median_absolute_error(y_test, y_pred))

# r2_score (判定系数，其含义是也是解释回归模型的方差得分，其值取值范围是[0,1]，越接近于1说明自变量越能解释因变量的方差变化，值越小则说明效果越差。)
print(r2_score(y_test, y_pred))
print(1 - (np.sum((y_test - y_pred) ** 2)) / np.sum((y_test - np.mean(y_test)) ** 2))

# mean_absolute_error
print(np.mean(np.abs(y_test - y_pred)))
print(mean_absolute_error(y_test, y_pred))

# mean_squared_log_error
print(mean_squared_log_error(y_test, y_pred))
print(np.mean((np.log(y_test + 1) - np.log(y_pred + 1)) ** 2))

# explained_variance_score
print(explained_variance_score(y_test, y_pred))
print(1 - np.var(y_test - y_pred) / np.var(y_test))
