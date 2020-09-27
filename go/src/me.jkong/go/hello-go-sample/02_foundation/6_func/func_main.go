package main

import (
	"fmt"
)

func main() {
	func1()

	func2("JKong")

	sum := func3(12, 23)
	fmt.Println(sum)

	sum = func4(1, 2, 3, 4, 5)
	fmt.Println(sum)

	sum = func5(0, 1, 2, 3, 4, 5)
	fmt.Println(sum)

	q, r := func6(13, 3)
	fmt.Println(q, r)

	fmt.Println(func7(3, 4, "x"))
}

// 返回异常信息
func func7(a, b int, op string) (int, error) {
	switch op {
	case "+":
		return a + b, nil
	case "-":
		return a - b, nil
	case "*":
		return a * b, nil
	case "/":
		return a / b, nil
	default:
		return -1, fmt.Errorf("unsupported operator: " + op)
	}
}

// 函数可以返回多个值
// 13 / 3 = 4 ... 1
func func6(a, b int) (int, int) {
	return a / b, a % b
}

// 可变参数
func func5(sum int, nums ...int) int {
	for _, v := range nums {
		sum += v
	}
	return sum
}

// 可变参数
func func4(num ...int) int {
	sum := 0
	for _, v := range num {
		sum += v
	}
	return sum
}

// 同类型参数可以放在一起
func func3(a, b int) int {
	return a + b
}

func func2(name string) {
	fmt.Println("hello", name)

}

func func1() {
	fmt.Println("hello world")
}
