// real server
// @author: Laba Zhang
package main

import (
	"encoding/base64"
	"log"
	"net/http"
	"os"
	"os/signal"
	"strings"
)

type WebHandler struct {
	Content string
}

func (h *WebHandler) ServeHTTP(w http.ResponseWriter, req *http.Request) {
	// 返回内容
	writeRep(w, req, h.Content)
}

func main() {
	signalChan := make(chan os.Signal)
	go (func() {
		log.Fatal(http.ListenAndServe(":8080", &WebHandler{Content: "Hello, world-8080!\n"}))
	})()

	go (func() {
		log.Fatal(http.ListenAndServe(":8081", &WebHandler{Content: "Hello, world-8081!\n"}))
	})()

	signal.Notify(signalChan, os.Interrupt)
	log.Fatalln(<-signalChan)
}

// 返回体处理
func writeRep(w http.ResponseWriter, req *http.Request, content string) {
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
