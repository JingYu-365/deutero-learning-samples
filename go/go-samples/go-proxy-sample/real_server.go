// real server
// @author: Laba Zhang
package main

import (
	"encoding/base64"
	"fmt"
	"go-proxy-sample/util"
	"log"
	"net/http"
	"os"
	"os/signal"
	"strings"
)

func main() {
	signalChan := make(chan os.Signal)
	go (func() {
		helloHandler := func(w http.ResponseWriter, req *http.Request) {
			content := "Hello, world-1!\n"
			// 返回内容
			writeRep(w, req, content)
		}
		http.HandleFunc("/a/hello", helloHandler)
		log.Fatal(http.ListenAndServe(":8080", nil))
	})()

	go (func() {
		helloHandler2 := func(w http.ResponseWriter, req *http.Request) {
			content := "Hello, world-2!\n"
			// 返回内容
			writeRep(w, req, content)
		}
		http.HandleFunc("/b/hello", helloHandler2)
		log.Fatal(http.ListenAndServe(":8081", nil))
	})()

	signal.Notify(signalChan, os.Interrupt)
	log.Fatalln(<-signalChan)
}

// 返回体处理
func writeRep(w http.ResponseWriter, req *http.Request, content string) {
	fmt.Println(util.GetRealRemoteIp(req))
	if authVerify(req) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(content))
	} else {
		w.Header().Set("WWW-Authenticate", `Basic realm="please input account and password!"`)
		w.WriteHeader(http.StatusUnauthorized)
		return
	}
}

// 添加 Basic Auth 验证
func authVerify(req *http.Request) bool {
	authHeader := req.Header.Get("Authorization")
	var authFlag = true
	split := strings.Split(authHeader, " ")
	if authHeader == "" {
		authFlag = false
	} else if len(split) != 2 || split[0] != "Basic" {
		authFlag = false
	} else if accountAndPwd, err := base64.StdEncoding.DecodeString(split[1]); string(accountAndPwd) != "laba:123456" || err != nil {
		authFlag = false
	}
	return authFlag
}
