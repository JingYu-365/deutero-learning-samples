# 类似java中map
d = {'Michael': 95, 'Bob': 75, 'Tracy': 85}
print(d)

# 根据key取值
print(d["Michael"])

# 修改指定值的value
d['Adam'] = 67
print(d)

d['Adam'] = 97
print(d)

# 如果key不存在而去获取值，则报错
if "JKong" in d:
    print(d["JKong"])

# 通过dict提供的get()方法，如果key不存在，可以返回None，或者自己指定的value
print(d.get('Thomas'))
print(d.get("Thomas", -1))

# 删除一个key，用pop(key)
d.pop("Adam")
print(d)
