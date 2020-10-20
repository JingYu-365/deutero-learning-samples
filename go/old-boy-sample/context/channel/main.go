package main

import (
	"fmt"
	"sync"
	"time"
)

// 使用 chan 方式实现主线程中断子线程
var exitChan chan bool
var wg sync.WaitGroup

func main() {
	exitChan = make(chan bool, 1)
	wg.Add(1)
	go f()
	time.Sleep(time.Second * 5)
	exitChan <- true
	wg.Wait()
}

func f() {
	defer wg.Done()

FORLOOP:
	for {
		fmt.Println("for loop!")
		time.Sleep(time.Millisecond * 500)

		select {
		case <-exitChan:
			// 指定调出for loop
			break FORLOOP
		default:
		}
	}
}
