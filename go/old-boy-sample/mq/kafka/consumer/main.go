package main

import (
	"log"
	"os"
	"os/signal"

	"github.com/Shopify/sarama"
)

func main() {
	// 创建消费者对象
	consumer, err := sarama.NewConsumer([]string{"127.0.0.1:9092"}, nil)
	if err != nil {
		panic(err)
	}

	// 程序运行结束时，调用Close关闭消费者对象
	defer func() {
		if err := consumer.Close(); err != nil {
			log.Fatalln(err)
		}
	}()
	// 创建消费者对象管理的分区消费者对象
	partitionConsumer, err := consumer.ConsumePartition("server-log", 0, sarama.OffsetNewest)
	if err != nil {
		panic(err)
	}

	// 程序运行结束时，调用Close关闭分区消费者对象
	defer func() {
		if err := partitionConsumer.Close(); err != nil {
			log.Println(err)
		}
	}()

	//用系统中断信号作为结束程序的信号
	signals := make(chan os.Signal, 1)
	signal.Notify(signals, os.Interrupt)

	consumed := 0
ConsumerLoop:
	for {
		select {
		// 消费者从分区中拿出数据消费
		case msg := <-partitionConsumer.Messages():
			log.Printf("Consumed message offset %d\n", msg.Offset)
			consumed++
		// 收到中断信号结束程序
		case <-signals:
			break ConsumerLoop
		}
	}
}
