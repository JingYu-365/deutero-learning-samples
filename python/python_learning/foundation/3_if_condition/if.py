# if 条件判断
# if <条件判断1>:
#     <执行1>
# elif <条件判断2>:
#     <执行2>
# elif <条件判断3>:
#     <执行3>
# else:
#     <执行4>

# if
age = 20
if age >= 18:
    print('your age is', age)
    print('adult')

# if ... else
age = 3
if age >= 18:
    print('your age is', age)
    print('adult')
else:
    print('your age is', age)
    print('teenager')

# if ... elif ... else
age = 3
if age >= 18:
    print('adult')
elif age >= 6:
    print('teenager')
else:
    print('kid')

#
birth = int(input('birth: '))
if birth < 2000:
    print('00前')
else:
    print('00后')
