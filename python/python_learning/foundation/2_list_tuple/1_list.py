classmates = ['Michael', 'Bob', 'Tracy']
print(classmates)

# len()
print(len(classmates))

# 按照下标取值
print(classmates[0])
print(classmates[1])
print(classmates[2])

# 指定位置插入数据，用insert()
classmates.insert(1, "JKong")
print(classmates)

# 删除list末尾的元素，用pop()
classmates.pop()
# 删除指定位置的元素，用pop(i)
classmates.pop(1)
print(classmates)

# 替换某个元素
classmates[1] = "JKong"
print(classmates)

# list里面的元素的数据类型也可以不同
list = ["JKong", 26, True, ["java", "go", "python"]]
print(list)

# 空list
list = []
print(len(list))
