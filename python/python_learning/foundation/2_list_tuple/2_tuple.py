# tuple和list非常类似，但是tuple一旦初始化就不能修改
classmates = ('Michael', 'Bob', 'Tracy')
print(classmates)

# 根据下标查询
print(classmates[0])
print(classmates[1])
print(classmates[2])

# 定义一个空的tuple，可以写成()
empty = ()
print(empty)

# 定义一个只有1个元素的tuple，定义时必须加一个逗号`,`
one = (1,)
print(one)

# 元组内容不可变，但是引用对象内容是可变的
t = ('a', 'b', ['A', 'B'])
t[2][0] = "JKong"
print(t)
