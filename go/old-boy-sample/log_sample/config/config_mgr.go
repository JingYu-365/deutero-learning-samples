// @Description: 配置信息初始化器
// @Author: JKong
// @Update: 2020/10/23 7:23 上午
package config

import (
	"github.com/coreos/etcd/clientv3"
	"time"
)

// 定义全局对象
var cli *clientv3.Client

// 从 ETCD 中拉取配置信息
func InitConfigManger(addr []string, timeout time.Duration) (err error) {
	cli, err = clientv3.New(clientv3.Config{
		Endpoints:   addr,
		DialTimeout: timeout,
	})
	return
}

func GetConfig(key string) (conf interface{}) {
	// todo
	return true
}
