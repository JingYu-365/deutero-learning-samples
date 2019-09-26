print('包含中文的str')

# ord()函数获取字符的整数表示
print(ord("A"))

# chr()函数把编码转换为对应的字符
print(chr(69))

# 十六进制字符
print('\u4e2d\u6587')

# 占位符
# %d	整数
# %f	浮点数
# %s	字符串
# %x	十六进制整数
print('Hello, %s' % 'world')
print('Hi, %s, you have $%d.' % ('Michael', 1000000))
# 不太确定应该用什么，%s永远起作用，它会把任何数据类型转换为字符串
print('Age: %s. Gender: %s' % (25, True))

# format()
print('Hello, {0}, 成绩提升了 {1:.1f}%'.format('小明', 17.125))

# byte 与string
print('中文'.encode('utf-8'))
print(b'\xe4\xb8\xad\xe6\x96\x87'.decode("utf-8"))
