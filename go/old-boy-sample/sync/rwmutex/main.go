package main

import (
	"fmt"
	"sync"
	"time"
)

var wg sync.WaitGroup

// 获取读写锁
var rwlock sync.RWMutex

var lock sync.Mutex

func main() {
	wg.Add(2)
	start := time.Now()
	go write()

	go read()

	wg.Wait()
	fmt.Println(time.Now().Sub(start))
}

var num = 0

func read() {
	for i := 0; i < 50000; i++ {
		// 加锁
		rwlock.RLock()
		// lock.Lock()
		if num%1000 == 0 {
			fmt.Println(num)
		}
		// 释放锁
		// lock.Unlock()
		rwlock.RUnlock()
	}
	wg.Done()
}

func write() {
	for i := 0; i < 500; i++ {
		// 加锁
		rwlock.Lock()
		// lock.Lock()
		num = num + 1
		// 释放锁
		rwlock.Unlock()
		// lock.Unlock()
	}
	wg.Done()
}
