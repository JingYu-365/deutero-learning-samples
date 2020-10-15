package main

import (
	"fmt"
	"sync"
)

var wg sync.WaitGroup

// 定义互斥锁
var lock sync.Mutex

func main() {
	wg.Add(2)
	go add()
	go add()
	wg.Wait()
	fmt.Println(num)
}

var num = 0

func add() {
	for i := 0; i < 50000; i++ {
		// 加锁
		lock.Lock()
		num = num + 1
		// 释放锁
		lock.Unlock()
	}
	wg.Done()
}
