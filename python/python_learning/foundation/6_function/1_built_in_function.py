# 使用help查看函数的使用方法
print(help(abs))

# 绝对值：abs
print(abs(-123))

# max 及 min
print(max(1, 2, 3, 4, 5, 6))
print(min(1, 2, 3, 4, 5, 6))

# 类型转换
print(int("123"))
print(int(12.34))
print(float("12.34"))
print(str(1.23))
print(str(100))
print(bool(1))
print(bool(0))
print(bool(""))
print(bool("asd"))

# 使用一个变量代替一个函数
a = abs
print(a(-123))
