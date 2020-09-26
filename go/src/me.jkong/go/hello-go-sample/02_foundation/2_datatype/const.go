package main

import "fmt"

// 定义常量： const 变量名 [变量类型]= 变量值
const pi = 3.14

// 批量声明常量
const (
	a = 100 // 如果不定义类型，如果是整型的数据，那么可以被看作是 int 或 float
	b = "asd"
	c = true
)

// const中每新增一行常量声明将使iota计数一次(iota可理解为const语句块中的行索引)。
const (
	aa = iota // iota == 0
	bb
	cc
)

const (
	_  = iota             // iota的初始值为0，每次使用都会递增一次
	KB = 1 << (10 * iota) // 1 << 10 左移10位 1024
	MB = 1 << (10 * iota) // 1 << 20 左移20位 1048576
	GB = 1 << (10 * iota) // 1 << 30 左移30位 1073741824
	TB = 1 << (10 * iota) // 1 << 40 左移40位 1099511627776
	PB = 1 << (10 * iota) // 1 << 50 左移50位 1125899906842624
)

func main() {
	fmt.Println(pi)

	fmt.Println(a, b, c)
	fmt.Println(aa, bb, cc)

	fmt.Println(KB, MB, GB, TB, PB)
}
