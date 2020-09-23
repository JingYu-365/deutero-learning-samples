#!/usr/bin/env python3
# _*_ coding: utf-8 _*_

"""
    TODO
"""

__author__ = 'JKong'

# In[1]
# 从csv载入房价数据
import pandas as pd

df = pd.read_csv('./boston_house.csv')

df.columns = ['CRIM', 'ZN', 'INDUS', 'CHAS',
              'NOX', 'RM', 'AGE', 'DIS', 'RAD',
              'TAX', 'PTRATIO', 'B', 'LSTAT', 'MEDV']
df.head()
# In[2]
from sklearn.model_selection import train_test_split

X = df.iloc[:, :-1].values
y = df['MEDV'].values

X_train, X_test, y_train, y_test = train_test_split(X, y,
                                                    random_state=0, test_size=0.3)
# In[4]
# 开始训练
from sklearn.linear_model import LinearRegression
import matplotlib.pyplot as plt

lr = LinearRegression()
lr.fit(X_train, y_train)
y_train_pred = lr.predict(X_train)  # 训练数据的预测值
y_test_pred = lr.predict(X_test)  # 测试数据的预测值
y_train_pred.shape, y_test_pred.shape
# In[5]
# 绘制散点图
plt.scatter(y_train_pred, y_train_pred - y_train,
            c='steelblue', marker='o', edgecolor='white',
            label='Training_data')
# plt.scatter(y_test_pred, y_test_pred - y_test,
#             c='limegreen', marker='s', edgecolor='white',
#             label='Test_data')
plt.xlabel('Predicted values')
plt.ylabel('Residuals')
plt.legend(loc='upper left')
plt.hlines(y=0, xmin=0, xmax=50, color='red', lw=2)
plt.xlim([0, 50])  # 设置坐标轴的取值范围
plt.tight_layout()
plt.show()
# In[6]
# 计算均方误差MSE、决定系数R2
from sklearn.metrics import r2_score
from sklearn.metrics import mean_squared_error

print("MSE of train: %.2f, test, %.2f" % (
    mean_squared_error(y_train, y_train_pred),
    mean_squared_error(y_test, y_test_pred)))

print("R^2 of train: %.2f, test, %.2f" % (
    r2_score(y_train, y_train_pred),
    r2_score(y_test, y_test_pred)))
