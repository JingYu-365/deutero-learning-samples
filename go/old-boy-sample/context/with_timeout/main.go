package main

import (
	"context"
	"fmt"
	"sync"
	"time"
)

/*
函数签名如下：
	func WithTimeout(parent Context, timeout time.Duration) (Context, CancelFunc)

WithTimeout返回WithDeadline(parent, time.Now().Add(timeout))。
取消此上下文将释放与其相关的资源，因此代码应该在此上下文中运行的操作完成后立即调用cancel，通常用于数据库或者网络连接的超时控制。
*/

var wg sync.WaitGroup

func worker(ctx context.Context) {
LOOP:
	for {
		fmt.Println("db connecting ...")
		// 假设正常连接数据库耗时10毫秒
		time.Sleep(time.Millisecond * 10)
		select {
		// 50毫秒超时后自动跳出循环
		case <-ctx.Done():
			break LOOP
		default:
		}
	}
	fmt.Println("worker done!")
	wg.Done()
}

func main() {
	// 设置一个50毫秒的超时
	ctx, cancel := context.WithTimeout(context.Background(), time.Millisecond*50)
	wg.Add(1)
	go worker(ctx)
	time.Sleep(time.Second * 5)
	cancel() // 通知子goroutine结束
	wg.Wait()
	fmt.Println("over")
}
