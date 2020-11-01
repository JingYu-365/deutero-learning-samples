// @Description: consul 工具类
// @Author: la ba zhang
// @Update: 2020/10/31 6:18 下午
package util

import (
	"fmt"
	consulapi "github.com/hashicorp/consul/api"
	"labazhang.me/config"
	"log"
)

var ConsulClient *consulapi.Client

func init() {
	// 创建连接consul服务配置
	conf := consulapi.DefaultConfig()
	conf.Address = config.ConsulAddr
	var err error
	ConsulClient, err = consulapi.NewClient(conf)
	if err != nil {
		fmt.Println("consul client error : ", err)
	}
}

func ServiceRegister() {
	// 创建注册到consul的服务到
	registration := consulapi.AgentServiceRegistration{}
	registration.ID = config.ServiceId
	registration.Name = config.ServiceName
	registration.Address = config.LocalIp
	registration.Port = config.LocalPort
	registration.Tags = []string{"primary"}

	// 增加consul健康检查回调函数
	check := new(consulapi.AgentServiceCheck)
	check.HTTP = fmt.Sprintf("http://%s:%d%s", registration.Address, registration.Port, "/health")
	check.Timeout = "5s"
	check.Interval = "5s"
	check.DeregisterCriticalServiceAfter = "30s" // 故障检查失败30s后 consul自动将注册服务删除
	registration.Check = check

	// 注册服务到consul
	err := ConsulClient.Agent().ServiceRegister(&registration)
	if err != nil {
		log.Fatal("register server error : ", err)
	}
}

func ServiceDeRegister() {
	err := ConsulClient.Agent().ServiceDeregister(config.ServiceId)
	if err != nil {
		log.Fatal(err)
	}
}

func ServiceFind() {
	services, err := ConsulClient.Agent().Services()
	if err != nil {
		log.Fatal(err)
	}
	for key, val := range services {
		fmt.Printf("key: %s\tvalue:%v \n", key, val)
	}
}
