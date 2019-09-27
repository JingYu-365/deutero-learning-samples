# 取 list 前3个元素，应该怎么做
L = ['Michael', 'Sarah', 'Tracy', 'Bob', 'Jack']

print(L[:3])
print(L[0:3])
print(L[1:3])

# 从后往前取
print(L[-1])
# 倒数第一个元素的索引是-1
print(L[-1:])

list_100 = list(range(100))
print(list_100)

# 取后10个数
print(list_100[-10:])
# 取10~20的数
print(list_100[10:20])

# 前10个数，每2个取1个数
print(list_100[:10:2])

# 取全部数据
print(list_100[:])

# tuple 切片
print((1, 2, 3, 4, 5, 6)[:3])
