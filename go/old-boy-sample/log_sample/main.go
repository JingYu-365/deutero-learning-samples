package main

import (
	"fmt"
	"log_sample/config"
	"log_sample/kafka"
	"log_sample/tail"

	"time"

	"gopkg.in/ini.v1"
)

var conf = new(config.AppConfig)

func main() {
	// 1. 加载配置文件
	err := ini.MapTo(conf, "log_sample/config/config.ini")
	if err != nil {
		fmt.Printf("load config.ini failed, err: %v \n", err)
		return
	}
	fmt.Println("init config success!")
	// 2. 初始化 kafka
	err = kafka.Init([]string{conf.Address})
	if err != nil {
		fmt.Printf("init kafka failed, err: %v \n", err)
		return
	}
	fmt.Println("init kafka success!")
	// 3. 初始化 tail log
	err = tail.Init(conf.Path)
	if err != nil {
		fmt.Printf("init tail failed, err: %v \n", err)
		return
	}
	fmt.Println("init tail log success!")
	// 4. 将tail收集的日志数据发送到kafka
	run()
}

func run() {
	// 从通道中读取日志，并将日志发送到Kafka中
	for {
		select {
		case line := <-tail.ReadChan():
			kafka.SendToKafka(conf.Topic, line.Text)
		default:
			time.Sleep(time.Second)
		}
	}
}
