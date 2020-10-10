package main

import (
	"fmt"
	"runtime"
	"sync"
)

var wg sync.WaitGroup

func main() {
	// 设置仅使用 一个CPU核心执行（默认是跑满CPU核心数）
	runtime.GOMAXPROCS(1)

	wg.Add(2)
	f1()
	f2()
	wg.Wait()

	// fl 和 f2 会按序执行
}

func f1() {
	defer wg.Done()
	for i := 0; i < 100; i++ {
		fmt.Printf("A: %d \n", i)
	}
}
func f2() {
	defer wg.Done()
	for i := 0; i < 100; i++ {
		fmt.Printf("B: %d \n", i)
	}
}
