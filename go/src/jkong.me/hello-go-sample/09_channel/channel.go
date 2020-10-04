// @Description: channel 示例
// @Author: JKong
// @Update: 2020/10/4 4:57 下午
package main

import (
	"fmt"
	"time"
)

func channelDemo() {
	ch := make(chan int)

	go func() {
		for {
			fmt.Println(<-ch)
		}
	}()

	for i := 0; i < 10; i++ {
		ch <- i
	}
}

func worker(ch chan int) {
	for {
		fmt.Printf("channel content: %c \n", <-ch)
	}
}

func channelForParam() {
	ch := make(chan int)
	go worker(ch)

	for i := 0; i < 10; i++ {
		ch <- i + 'a'
	}

	for i := 0; i < 10; i++ {
		ch <- i + 'A'
	}

}

func channelForReturn() {
	const channelSize = 10
	var channels [channelSize]chan<- int

	for i := 0; i < channelSize; i++ {
		// 创建worker channel， 并将具体业务逻辑作为参数传递
		channels[i] = createWorkerChannel(doWork, i)
	}

	for i := 0; i < channelSize; i++ {
		channels[i] <- i + 'a'
	}
}

// chan<- int: 表名此返回的chan是用来接收数据的
func createWorkerChannel(fun func(chan int, int), i int) chan<- int {
	ch := make(chan int)
	go fun(ch, i)
	return ch
}

// 具体业务逻辑
func doWork(ch chan int, i int) {
	for {
		// 判断channel是否已经由发送方关闭，如果关闭则 ok = false
		con, ok := <-ch
		if ok {
			fmt.Printf("worker id: %d, channel content: %c \n", i, con)
		} else {
			break
		}
	}
}

func bufferedChannel() {
	// 指定channel的缓冲区为3
	ch := make(chan int, 3)
	go doWork(ch, 0)
	for i := 0; i < 10; i++ {
		ch <- 'a' + i
	}
}

func closeChannel() {
	ch := make(chan int)
	go doWork(ch, 0)
	for i := 0; i < 10; i++ {
		ch <- 'a' + i
	}
	close(ch)
}

func main() {
	//channelDemo()

	//channelForParam()

	//channelForReturn()

	//bufferedChannel()

	closeChannel()

	time.Sleep(time.Second)
}
