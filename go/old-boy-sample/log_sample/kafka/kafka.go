package kafka

import (
	"fmt"

	"github.com/Shopify/sarama"
)

var client sarama.SyncProducer

// Init 初始化 kafka SyncProducer
func Init(addr []string) (err error) {
	config := sarama.NewConfig()
	config.Producer.RequiredAcks = sarama.WaitForAll
	config.Producer.Partitioner = sarama.NewRandomPartitioner
	config.Producer.Return.Successes = true
	client, err = sarama.NewSyncProducer(addr, config)
	if err != nil {
		fmt.Println("Producer closed ,err :", err)
		return
	}
	return
}

// SendToKafka 将数据发送到kafka指定topic
func SendToKafka(topic, data string) {
	msg := &sarama.ProducerMessage{}
	msg.Topic = topic
	msg.Value = sarama.StringEncoder(data)

	pid, offset, err := client.SendMessage(msg)
	if err != nil {
		fmt.Println("send msg failed, err:", err)
		return
	}
	fmt.Printf("pid:%v, offset:%v\n", pid, offset)
}
