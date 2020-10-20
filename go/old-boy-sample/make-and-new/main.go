package main

import "fmt"

func main() {
	makeDemo()

	newDemo()
}

// make 关键字是针对 map | slice | channel 进行内存空间开辟的
func makeDemo() {
	m := make(map[string]string)
	s := make([]int, 10)
	ch := make(chan int)
	fmt.Printf("%T,\n %T,\n %T \n", m, s, ch)
}

// new 关键字是对 基本数据类型 | struct 进行内存空间开辟的
func newDemo() {
	// new 返回的是内存地址
	i := new(int)
	fmt.Printf("%T, \n %p, \n %d \n", i, i, *i)

	s := new(struct {
		name string
		int
	})
	fmt.Printf("%T, \n %p, \n %v \n", s, s, *s)
}
