// proxy server
// @author: Laba Zhang
package main

import (
	"fmt"
	"go-proxy-sample/config"
	"go-proxy-sample/util"
	"log"
	"net/http"
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
			request, _ := http.NewRequest(r.Method, pass+r.URL.Path, r.Body)
			util.CopyHeader(r.Header, &request.Header)
			// 设置真实代理地址
			util.SettingXForwardedFor(*r, request)
			fmt.Println("123")
			err := util.DoRequest(request, w)
			if err != nil {
				w.WriteHeader(http.StatusInternalServerError)
				w.Write([]byte("internal server error!"))
			}
		}
	}
	if reqSuccess {
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
