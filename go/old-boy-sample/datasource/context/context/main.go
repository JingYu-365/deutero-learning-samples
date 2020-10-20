package main

import (
	"context"
	"fmt"
	"sync"
	"time"
)

// 使用 context 方式实现主线程中断子线程

var wg sync.WaitGroup

func main() {
	ctx, cancel := context.WithCancel(context.Background())
	wg.Add(1)
	go f(ctx)
	time.Sleep(time.Second * 5)
	cancel()
	wg.Wait()
}

func f(ctx context.Context) {
	defer wg.Done()
	go f2(ctx)

FORLOOP:
	for {
		fmt.Println("for loop-1!")
		time.Sleep(time.Millisecond * 500)

		select {
		case <-ctx.Done():
			// 指定调出for loop
			break FORLOOP
		default:
		}
	}
}

// 当 cancel被调用时，子循环 f 与 f2 都会被停止
func f2(ctx context.Context) {
	defer wg.Done()

FORLOOP:
	for {
		fmt.Println("for loop-2!")
		time.Sleep(time.Millisecond * 500)

		select {
		case <-ctx.Done():
			// 指定调出for loop
			break FORLOOP
		default:
		}
	}
}
