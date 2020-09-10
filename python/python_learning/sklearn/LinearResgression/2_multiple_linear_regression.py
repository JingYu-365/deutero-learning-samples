#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    多元线性回归
    为一元线性回归模型载增加一个解释变量，此时用一元线性回归已经无法解决了，我们可以用更具一般性的模型来表示，即多元线性回归
            y=α+β1*X1+β2*X2+.....+βn*Xn
    写成矩阵形式如下:
            Y=βX
    其中，
    - Y是训练集的响应变量列向量
    - β是模型参数列向量
    - X称为设计矩阵，是m × n维训练集的解释变量矩阵。
    m是训练集样本数量， n是解释变量个数。
"""

__author__ = 'JKong'

from sklearn.linear_model import LinearRegression

'''
训练数据：
样本      直径      辅料种类        价格
1         6           2          7
2         8           1          9
3         10          0          13
4         14          2          17.5
5         18          0          18
'''

X = [[6, 2], [8, 1], [10, 0], [14, 2], [18, 0]]
y = [[7], [9], [13], [17.5], [18]]
model = LinearRegression()
model.fit(X, y)
X_test = [[8, 2], [9, 0], [11, 2], [16, 2], [12, 0]]
y_test = [[11], [8.5], [15], [18], [11]]
predictions = model.predict(X_test)
for i, prediction in enumerate(predictions):
    print('Predicted: {}, Target: {}'.format(prediction, y_test[i]))
print('R-squared: {:.2f}'.format(model.score(X_test, y_test)))
