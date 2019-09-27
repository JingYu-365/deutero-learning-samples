# 自定义函数返回多个值
import math


def move(x, y, step, angle=0):
    nx = x + step * math.cos(angle)
    ny = y - step * math.sin(angle)
    return nx, ny


print(move(100, 100, 60, math.pi / 6))

# Python函数返回的仍然是单一值
r = move(100, 100, 60, math.pi / 6)
print(r)
print(type(r))  # <class 'tuple'>
