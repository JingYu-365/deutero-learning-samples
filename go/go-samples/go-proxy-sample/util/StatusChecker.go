// 节点状态监测
// @author: Laba Zhang
package util

import (
	"log"
	"net/http"
	"time"
)

type HttpChecker struct {
	Servers    map[string]HttpServerSlice
	FailMax    int
	SuccessMax int
}

func NewHttpChecker(servers map[string]HttpServerSlice) *HttpChecker {
	return &HttpChecker{
		Servers: servers,
		FailMax: 5, // 暂时设置为5
	}
}

func (hc *HttpChecker) AddHttpServer(httpServer *HttpServer) {
	if _, ok := hc.Servers[httpServer.Path]; !ok {
		hc.Servers[httpServer.Path] = make([]*HttpServer, 0)
	}
	hc.Servers[httpServer.Path] = append(hc.Servers[httpServer.Path], httpServer)
}

// Check 检测代理健康状态
func (hc *HttpChecker) Check(timeout time.Duration) {
	client := http.Client{Timeout: timeout}
	for _, proxies := range hc.Servers {
		for _, proxy := range proxies {
			resp, err := client.Head(proxy.Proxy)
			if resp != nil {
				defer resp.Body.Close()
			}

			if err == nil && resp != nil && resp.StatusCode >= 200 && resp.StatusCode < 500 {
				hc.Success(proxy)
				log.Printf("server: %s, status: %s, times: %d", proxy.Proxy, proxy.Status, proxy.FailCount)
			} else {
				hc.Fail(proxy)
				log.Printf("server: %s, status: %s, times: %d", proxy.Proxy, proxy.Status, proxy.FailCount)
			}
		}
	}
}

func (hc *HttpChecker) Fail(server *HttpServer) {
	if server.FailCount == hc.FailMax {
		server.Status = "DOWN"
	} else {
		server.FailCount++
	}
	server.SuccessCount = 0
}

func (hc *HttpChecker) Success(server *HttpServer) {
	if server.FailCount > 0 {
		server.FailCount--
		server.SuccessCount++
		if server.SuccessCount == hc.SuccessMax {
			server.FailCount = 0
			server.Status = "UP"
			server.SuccessCount = 0
		}
	} else {
		server.Status = "UP"
	}
}
