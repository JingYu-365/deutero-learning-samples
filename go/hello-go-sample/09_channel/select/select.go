// @Description: Select Operation
// @Author: JKong
// @Update: 2020/10/4 7:01 下午
package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	// select 基础使用
	//selectDemo()

	ConsumerWithProducer()
}

// 代码逻辑：
// 1. 两个消费者每1.5s内随机生成一条数据发送到 channel中
// 2. 一个worker每1s消费一个消息
// 3. 程序运行10s后退出
// 4. 如果800ms没有收到消息，将抛出超时信息
// 5. 每2s输出一下队列长度
func ConsumerWithProducer() {
	ch1, ch2 := generateChannel(), generateChannel()
	worker := createWorker()

	var values []int
	var activeWorker chan<- int
	var activeValue int

	// 设置程序运行10s之后停止
	after := time.After(10 * time.Second)

	// 每两秒输出队列长度
	tick := time.Tick(2000 * time.Millisecond)
	for {
		if len(values) > 0 {
			activeWorker = worker
			activeValue = values[0]
		}
		select {
		case n := <-ch1:
			values = append(values, n)
		case n := <-ch2:
			values = append(values, n)
		case activeWorker <- activeValue:
			values = values[1:]

		case <-tick:
			fmt.Println("queue len = ", len(values))
		// 如果在 100ms 内没有收到消息，就抛出超时信息
		case <-time.After(800 * time.Millisecond):
			fmt.Println("timeout!")
		case <-after:
			fmt.Println("Exist!")
			return
		}
	}
}

func worker(c chan int) {
	for n := range c {
		time.Sleep(time.Second)
		fmt.Printf("Worker received %d\n", n)
	}
}

func createWorker() chan<- int {
	c := make(chan int)
	go worker(c)
	return c
}

func generateChannel() chan int {
	ch := make(chan int)
	go createMessage(&ch)
	return ch
}
func createMessage(ch *chan int) {
	i := 0
	for {
		time.Sleep(time.Duration(rand.Intn(1500)) * time.Millisecond)
		*ch <- i
		i++
	}
}

func selectDemo() {
	ch1, ch2 := generateChannel(), generateChannel()

	for {
		select {
		case n := <-ch1:
			fmt.Printf("channel 1 create message: %d \n", n)
		case n := <-ch2:
			fmt.Printf("channel 2 create message: %d \n", n)
			// 如果当所有case都没有任何消息时，执行此default
			//default:
			//	fmt.Println("no channel has message!")
		}
	}
}
