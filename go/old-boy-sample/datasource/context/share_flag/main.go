package main

import (
	"fmt"
	"sync"
	"time"
)

// 使用flag 方式实现主线程中断子线程
var flag = false
var wg sync.WaitGroup

func main() {
	wg.Add(1)
	go f()

	time.Sleep(time.Second * 5)
	flag = true
	wg.Wait()
}

func f() {
	defer wg.Done()

	for {
		fmt.Println("for loop!")
		time.Sleep(time.Millisecond * 500)

		if flag {
			break
		}
	}
}
