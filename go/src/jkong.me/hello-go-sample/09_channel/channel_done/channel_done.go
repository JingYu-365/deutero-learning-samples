// @Description: sync 中等待所有协程任务完成在退出 WaitGroup
// @Author: JKong
// @Update: 2020/10/4 6:17 下午
package main

import (
	"fmt"
	"sync"
)

func main() {
	channelForWaiting()
}

func channelForWaiting() {
	var workers [10]Worker
	var wg sync.WaitGroup
	for i := 0; i < 10; i++ {
		// 创建worker channel， 并将具体业务逻辑作为参数传递
		workers[i] = createWorker(&wg)
	}
	// 等待的协程数
	wg.Add(10)
	for i, worker := range workers {
		worker.in <- i + 'a'
	}
	// 等待
	wg.Wait()
}

// chan<- int: 表名此返回的chan是用来接收数据的
func createWorker(wg *sync.WaitGroup) Worker {
	worker := Worker{
		in: make(chan int),
		wg: wg,
	}
	go worker.DoWork()
	return worker
}

// 定义work的内容
type Work interface {
	DoWork()
	Done()
}

// 定义worker机构体，并实现Work接口
type Worker struct {
	in chan int
	wg *sync.WaitGroup
}

func (worker *Worker) Done() {
	worker.wg.Done()
}

// 具体业务逻辑
func (worker *Worker) DoWork() {
	for {
		// 判断channel是否已经由发送方关闭，如果关闭则 ok = false
		con, ok := <-worker.in
		if ok {
			fmt.Printf("channel content: %c \n", con)
		} else {
			break
		}
		worker.Done()
	}
}
