package main

import (
	"log"
	"os"
	"os/signal"
	"sync"

	"github.com/Shopify/sarama"
)

func main() {
	config := sarama.NewConfig()
	// 启用发送成功配置
	config.Producer.Return.Successes = true
	// 启动一个异步生产者
	producer, err := sarama.NewAsyncProducer([]string{"localhost:9092"}, config)
	if err != nil {
		panic(err)
	}
	log.SetOutput(os.Stdout)
	//接收终端的中断命令（ctrl C）来正常退出
	signals := make(chan os.Signal, 1)
	signal.Notify(signals, os.Interrupt)

	var (
		wg                          sync.WaitGroup
		enqueued, successes, errors int
	)

	// 开启Goroutine读取成功管道内的消息
	wg.Add(1)
	go func() {
		defer wg.Done()
		for range producer.Successes() {
			successes++
		}
	}()

	// 开启Goroutine读取错误管道内的消息
	wg.Add(1)
	go func() {
		defer wg.Done()
		for err := range producer.Errors() {
			log.Println(err)
			errors++
		}
	}()

ProducerLoop:
	for {
		// 消息指针
		message := &sarama.ProducerMessage{Topic: "server-log", Value: sarama.StringEncoder("hello sarama kafka")}
		select {
		// 发送消息
		case producer.Input() <- message:
			enqueued++
		// 正常退出
		case <-signals:
			producer.AsyncClose()
			break ProducerLoop
		}
	}
	//等待两个管道内的消息处理完
	wg.Wait()
	log.Printf("Successfully produced: %d; errors: %d; produced: %d\n", successes, errors, enqueued)
}
