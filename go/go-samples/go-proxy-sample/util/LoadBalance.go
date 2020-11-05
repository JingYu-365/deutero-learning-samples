// 负载均衡器
// @author: Laba Zhang
package util

import (
	"errors"
	"fmt"
	"math/rand"
	"regexp"
	"time"
)

var LB = NewLoadBalance()

type HttpServer struct {
	Path  string
	Proxy string
}

func NewHttpServer(path, proxy string) *HttpServer {
	return &HttpServer{
		Path:  path,
		Proxy: proxy,
	}
}

type LoadBalance struct {
	Servers map[string][]*HttpServer
}

func NewLoadBalance() *LoadBalance {
	return &LoadBalance{
		Servers: make(map[string][]*HttpServer, 0),
	}
}

// AddHttpServer 添加HTTPServer到lb中
func (lb *LoadBalance) AddHttpServer(server *HttpServer) {
	lb.Servers[server.Path] = append(lb.Servers[server.Path], server)
}

// 随机负载
func (lb *LoadBalance) SelectForRandom(path string) (*HttpServer, error) {
	rand.Seed(time.Now().Unix())
	for pathTmp, proxies := range lb.Servers {
		if matched, _ := regexp.MatchString(pathTmp, path); matched {
			index := rand.Intn(len(lb.Servers[pathTmp]))
			fmt.Println(len(lb.Servers[pathTmp]), index)
			return proxies[index], nil
		}
	}
	return nil, errors.New("not found matched url")
}
