// 负载均衡器
// @author: Laba Zhang
package util

import (
	"errors"
	"math/rand"
	"regexp"
	"time"
)

var LB = NewLoadBalance()

type HttpServer struct {
	// 请求路径
	Path string
	// 被代理地址
	Proxy string
	// 权重
	Weight int
}

func NewHttpServer(path, proxy string, weight int) *HttpServer {
	// 默认权重值为1
	if weight == 0 {
		weight = 1
	}
	return &HttpServer{
		Path:   path,
		Proxy:  proxy,
		Weight: weight,
	}
}

type LoadBalance struct {
	Servers      map[string][]*HttpServer
	CurrentIndex map[string]int
}

func NewLoadBalance() *LoadBalance {
	return &LoadBalance{
		Servers:      make(map[string][]*HttpServer, 0),
		CurrentIndex: make(map[string]int, 0),
	}
}

// AddHttpServer 添加HTTPServer到lb中
func (lb *LoadBalance) AddHttpServer(server *HttpServer) {
	lb.Servers[server.Path] = append(lb.Servers[server.Path], server)
	// 初始LB中的轮训下标
	if _, ok := lb.CurrentIndex[server.Path]; !ok {
		lb.CurrentIndex[server.Path] = 0
	}
}

// 随机负载
// 在注册进来的URL映射中，随机选择一个被代理的节点进行访问
func (lb *LoadBalance) SelectForRandom(path string) (*HttpServer, error) {
	rand.Seed(time.Now().Unix())
	for pathTmp, proxies := range lb.Servers {
		// 根据路径前缀进行匹配
		if matched, _ := regexp.MatchString(pathTmp, path); matched {
			index := rand.Intn(len(lb.Servers[pathTmp]))
			return proxies[index], nil
		}
	}
	return nil, errors.New("not found matched url")
}

// 带权重的随机负载
// proxy-1：weight：5
// proxy-2：weight：2
// proxy-3：weight：2
// 那么，
// 随机数在范围[0,5)时，路由到proxy-1，
// 随机数在范围[5,7)时，路由到proxy-2，
// 随机数在范围[7,9)时，路由到proxy-3
func (lb *LoadBalance) SelectForWeightRandom(path string) (*HttpServer, error) {
	rand.Seed(time.Now().Unix())
	for pathTmp, proxies := range lb.Servers {
		// 根据路径前缀进行匹配
		if matched, _ := regexp.MatchString(pathTmp, path); matched {
			// 计算weight的和
			weightSum := 0
			sumList := make([]int, len(lb.Servers[pathTmp]))
			for i, proxy := range proxies {
				weightSum += proxy.Weight
				sumList[i] = weightSum
			}
			// 选出被代理的地址
			index := rand.Intn(weightSum)
			for i, sum := range sumList {
				// 判断当前选择的随机数是否在当前节点的权重范围内，如果在，则将当前的server返回
				if index < sum {
					return proxies[i], nil
				}
			}
			return proxies[0], nil
		}
	}
	return nil, errors.New("not found matched url")
}

// 简单轮询策略
func (lb *LoadBalance) SelectForRoundRobin(path string) (*HttpServer, error) {
	for pathTmp, proxies := range lb.Servers {
		// 根据路径前缀进行匹配
		if matched, _ := regexp.MatchString(pathTmp, path); matched {
			server := proxies[lb.CurrentIndex[pathTmp]]
			lb.CurrentIndex[pathTmp] = (lb.CurrentIndex[pathTmp] + 1) % len(proxies)
			return server, nil
		}
	}
	return nil, errors.New("not found matched url")
}

// 加权轮训策略
func (lb *LoadBalance) SelectForWeightRoundRobin(path string) (*HttpServer, error) {
	for pathTmp, proxies := range lb.Servers {
		// 根据路径前缀进行匹配
		if matched, _ := regexp.MatchString(pathTmp, path); matched {
			// 计算权重
			weightSum := 0
			sumList := make([]int, len(lb.Servers[pathTmp]))
			for i, proxy := range proxies {
				weightSum += proxy.Weight
				sumList[i] = weightSum
			}
			for i, weight := range sumList {
				if lb.CurrentIndex[pathTmp] < weight {
					server := proxies[i]
					lb.CurrentIndex[pathTmp] = (lb.CurrentIndex[pathTmp] + 1) % weightSum
					return server, nil
				}
			}
		}
	}
	return nil, errors.New("not found matched url")
}
