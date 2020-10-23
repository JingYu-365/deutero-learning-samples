// @Description: 实现keep alive
// @Author: JKong
// @Update: 2020/10/22 7:03 上午
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
		DialTimeout: time.Second * 5,
	})
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("connect to etcd success.")
	defer cli.Close()

	// 5 秒的续租
	resp, err := cli.Grant(context.TODO(), 5)
	if err != nil {
		log.Fatal(err)
	}

	_, err = cli.Put(context.TODO(), "/grant/keepalive", "keepalive", clientv3.WithLease(resp.ID))
	if err != nil {
		log.Fatal(err)
	}

	// the key 'foo' will be kept forever
	ch, kaerr := cli.KeepAlive(context.TODO(), resp.ID)
	if kaerr != nil {
		log.Fatal(kaerr)
	}
	for {
		ka := <-ch
		fmt.Println("ttl:", ka.TTL)
		time.Sleep(time.Second)
	}
}
