package main

import "fmt"

type myInt int    // 自定义类型
type youInt = int // 类型别名

func main() {
	// 自定义类型
	var n myInt
	n = 100
	fmt.Println(n)
	fmt.Printf("%T\n", n)

	// 类型别名
	var m youInt
	m = 100
	fmt.Println(m)
	fmt.Printf("%T \n", m)

	// console:
	// 100
	// main.myInt
	// 100
	// int

	var c rune
	c = '空'
	fmt.Println(c)
	fmt.Printf("%T \n", c)

	// console:
	// 31354
	// int32
}
