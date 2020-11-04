// proxy server
// @author: Laba Zhang
package main

import (
	"go-proxy-sample/config"
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
	"regexp"
)

type ProxyHandler struct {
}

func (*ProxyHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	defer func() {
		if err := recover(); err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			w.Write([]byte("internal error!"))
			log.Fatalln(err)
		}
	}()

	var reqSuccess bool
	for path, pass := range config.ProxyConfig {
		if matched, _ := regexp.MatchString(path, r.URL.Path); matched {
			reqSuccess = true
			// 使用 go http client 实现
			//request, _ := http.NewRequest(r.Method, pass+r.URL.Path, r.Body)
			//util.CopyHeader(r.Header, &request.Header)
			//// 设置真实代理地址
			//util.SettingXForwardedFor(*r, request)
			//err := util.DoRequest(request, w)

			// 使用 go 内置反向代理，保留原URL，并替换掉host:port
			parsedUrl, _ := url.Parse(pass)
			proxy := httputil.NewSingleHostReverseProxy(parsedUrl)
			proxy.ServeHTTP(w, r)
			return
		}
	}
	if !reqSuccess {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte("illegal url"))
		return
	}
}

func main() {
	err := http.ListenAndServe(":2365", &ProxyHandler{})
	if err != nil {
		log.Printf("failed to start server at: %d \n", 2365)
	}
}
