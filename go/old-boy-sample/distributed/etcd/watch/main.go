package main

import (
	"context"
	"fmt"
	"time"

	"go.etcd.io/etcd/clientv3"
)

func main() {
	cli, err := clientv3.New(clientv3.Config{
		Endpoints:   []string{"127.0.0.1:2379"},
		DialTimeout: 5 * time.Second,
	})

	if err != nil {
		fmt.Printf("connect to etcd failed, err: %v \n", err)
		return
	}

	fmt.Println("connect to etcd success!")
	defer cli.Close()

	// 注册watch事件
	watcher := cli.Watch(context.Background(), "user")
	for watchResp := range watcher {
		for _, event := range watchResp.Events {
			fmt.Printf("Event type: %s, Key: %s, Value: %s \n", event.Type, event.Kv.Key, event.Kv.Value)
		}
	}
	/*
		在控制台输出：
		zhdh@192 ~ % etcdctl --endpoints=http://localhost:2379 put user "xiaoming"
		OK
		zhdh@192 ~ % etcdctl --endpoints=http://localhost:2379 put user "xiaohua"
		OK
		zhdh@192 ~ % etcdctl --endpoints=http://localhost:2379 del user

		程序输出：
		Event type: PUT, Key: user, Value: xiaoming
		Event type: PUT, Key: user, Value: xiaohua
		Event type: DELETE, Key: user, Value:
	*/
}
