#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    训练数据和测试数据随机选取
"""

__author__ = 'JKong'

import numpy as np
import sklearn.model_selection

data_x = [['this is class 1 ']] * 10 + [['this is class 2']] * 5
data_y = [[1]] * 10 + [[2]] * 5
print(data_y)

X_train, X_test, y_train, y_test = \
    sklearn.model_selection.train_test_split(data_x, data_y, test_size=0.3, random_state=2)

print(y_train)
print(y_test)

print('numbers of positive class in training data:', sum(np.mat(y_train) == 1)[0], '/', len(y_train))
print('numbers of negative class in training data:', sum(np.mat(y_train) == 2)[0], '/', len(y_train))
print('numbers of positive class in test data:', sum(np.mat(y_test) == 1), '/', len(y_test))
