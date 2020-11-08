// 负载均衡器
// @author: Laba Zhang
package util

import (
	"errors"
	"math/rand"
	"regexp"
	"sort"
	"time"
)

var LB = NewLoadBalance()

type HttpServer struct {
	// 请求路径
	Path string
	// 被代理地址
	Proxy string
	// 实际权重
	Weight int
	// 当前权重
	CurWeight int
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
		// 每次在轮训之前会将 CurWeight + Weight，所以此处将CurWeight初始化为0
		CurWeight: 0,
	}
}

type HttpServerSlice []*HttpServer

func (p HttpServerSlice) Len() int           { return len(p) }
func (p HttpServerSlice) Less(i, j int) bool { return p[i].CurWeight > p[j].CurWeight }
func (p HttpServerSlice) Swap(i, j int)      { p[i], p[j] = p[j], p[i] }

type LoadBalance struct {
	Servers map[string]HttpServerSlice
	// 轮训的下标
	CurrentIndex map[string]int
	// 当前路由的proxy的总权重值
	SumWeight map[string]int
}

func NewLoadBalance() *LoadBalance {
	return &LoadBalance{
		Servers:      make(map[string]HttpServerSlice, 0),
		CurrentIndex: make(map[string]int, 0),
		SumWeight:    make(map[string]int, 0),
	}
}

// AddHttpServer 添加HTTPServer到lb中
func (lb *LoadBalance) AddHttpServer(server *HttpServer) {
	lb.Servers[server.Path] = append(lb.Servers[server.Path], server)
	// 初始LB中的轮训下标
	if _, ok := lb.CurrentIndex[server.Path]; !ok {
		lb.CurrentIndex[server.Path] = 0
	}
	// 累计LB中的SumWeight
	if _, ok := lb.CurrentIndex[server.Path]; !ok {
		lb.CurrentIndex[server.Path] = server.Weight
	} else {
		lb.SumWeight[server.Path] = lb.SumWeight[server.Path] + server.Weight
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

// 平滑加权轮训策略
// 带权重的随机负载
// proxy-1：weight：5
// proxy-2：weight：2
// proxy-3：weight：2
// 那么，执行的顺序为：[1,1,1,1,1,2,2,3,3]
// 那么也可以是：[1,2,1,3,1,2,1,3,1] 的执行顺序，
// 那么相比第一种，第二种的执行顺序能减轻对副武器的压力
func (lb *LoadBalance) SelectForSoftWeightRoundRobin(path string) (*HttpServer, error) {
	for pathTmp, proxies := range lb.Servers {
		// 根据路径前缀进行匹配
		if matched, _ := regexp.MatchString(pathTmp, path); matched {
			// 计算权重
			weightSum := lb.SumWeight[pathTmp]
			for _, proxy := range proxies {
				proxy.CurWeight = proxy.CurWeight + proxy.Weight
			}
			sort.Sort(proxies)
			proxies[0].CurWeight = proxies[0].CurWeight - weightSum
			return proxies[0], nil
		}
	}
	return nil, errors.New("not found matched url")
}
