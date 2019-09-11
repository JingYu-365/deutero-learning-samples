package main

import "fmt"
// 匿名函数
func main() {
	func1 := func() {
		fmt.Println("func 1")
	}
	func1()

	fmt.Println()
	func() {
		fmt.Println("func 2")
	}()
}
