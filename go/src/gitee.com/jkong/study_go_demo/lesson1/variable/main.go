package main

import "fmt"

// 全局变量
var m = 100

func main() {
	// 变量声明格式：var 变量名 变量类型
	// 声明变量必需要使用，否者会报错
	var name string
	var age int
	var isOk bool

	fmt.Println(name, age, isOk)

	// 对变量进行批量定义
	var (
		a string // ""
		b int    // 0
		c bool   // false
	)
	a = "Hello world"
	b = 123
	c = true
	fmt.Println(a, b, c)

	// 变量类型推导
	var class = "class 1"
	fmt.Println(class)

	// 简化声明变量
	nameStr := "JKong"
	fmt.Println(nameStr)

	// 使用全局变量
	fmt.Println(m)

	// _ 用来接受不需要的变量
	aa, _ := foo()
	fmt.Println(aa)
}

func foo() (int, string) {
	return 123,"123456"
}