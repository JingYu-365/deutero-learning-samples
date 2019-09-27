# 计算阶乘n! = 1 x 2 x 3 x ... x n，用函数fact(n)表示


def fact(x):
    if not isinstance(x, int):
        raise TypeError('bad operand type')
    if x == 1:
        return 1
    return x * fact(x - 1)


print(fact(10))

# 递归函数的优点是逻辑简单清晰，缺点是过深的调用会导致栈溢出
print(fact(1000))  # RecursionError: maximum recursion depth exceeded while calling a Python object
