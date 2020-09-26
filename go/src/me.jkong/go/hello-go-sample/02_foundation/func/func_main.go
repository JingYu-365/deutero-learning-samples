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
}

func func5(sum int, nums ...int) int {
	for _, v := range nums {
		sum += v
	}
	return sum
}

func func4(num ...int) int {
	sum := 0
	for _, v := range num {
		sum += v
	}
	return sum
}

func func3(a, b int) int {
	return a + b
}

func func2(name string) {
	fmt.Println("hello", name)

}

func func1() {
	fmt.Println("hello world")
}
