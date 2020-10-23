// @Description: 实现使用go操作etcd的lease
// @Author: JKong
// @Update: 2020/10/22 6:35 上午
package main

import (
	"context"
	"fmt"
	"go.etcd.io/etcd/clientv3"
	"log"
	"time"
)

func main() {
	cli, err := clientv3.New(clientv3.Config{
		Endpoints:   []string{"127.0.0.1:2379"},
		DialTimeout: 5 * time.Second,
	})

	if err != nil {
		log.Fatalf("connect to etcd err, err: %v \n", err)
		return
	}
	log.Println("connect to etcd success!")
	defer cli.Close()

	// 创建一个5秒的续租
	grant, err := cli.Grant(context.TODO(), 5)
	if err != nil {
		log.Fatalf("init etcd grant failed, err: %v \n", err)
		return
	}

	// 创建一个key，5 秒钟后就会被移除
	var key = "/grant/test"
	_, err = cli.Put(context.TODO(), key, "5 second grant", clientv3.WithLease(grant.ID))
	if err != nil {
		log.Fatal(err)
	}

	time.Sleep(time.Second * 4)
	getKey(cli, key)
	// 由于网络原因，此处设置1s，会出现此key仍然存在的情况
	time.Sleep(time.Second * 2)
	getKey(cli, key)
}

func getKey(cli *clientv3.Client, key string) {
	resp, err := cli.Get(context.TODO(), key)
	if err != nil {
		log.Println(err)
	}
	if len(resp.Kvs) == 0 {
		log.Fatal("no key found!")
	}
	for _, ev := range resp.Kvs {
		fmt.Printf("%s: %s\n", ev.Key, ev.Value)
	}
}
