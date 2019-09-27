# 要创建一个set，需要提供一个list作为输入集合：
s = set([1, 2, 3])
print(s)

# 重复元素在set中自动被过滤：
s = set([1, 1, 2, 2, 3, 3])
print(s)

# 通过add(key)方法可以添加元素到set
s.add(4)
print(s)

# 通过remove(key)方法可以删除元素
s.remove(4)
print(s)

# set可以看成数学意义上的无序和无重复元素的集合，因此，两个set可以做数学意义上的交集、并集等操作:
s1 = {1, 2, 3}
s2 = {2, 3, 4}
# 求交集
print(s1 & s2)
# 求并集
print(s1 | s2)
