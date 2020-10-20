// @Description: 使用 go 闭包能力实现累加器
// @Author: JKong
// @Update: 2020/10/3 3:05 下午
package main

import "fmt"

func getAdder() func(num int) int {
	var sum int
	// 定义累加逻辑
	return func(num int) int {
		sum += num
		return sum
	}
}

func main() {
	// 获取累加器
	adder := getAdder()
	for i := 0; i < 10; i++ {
		fmt.Printf("0 + ... + %d = %d \n", i, adder(i))
	}
}
