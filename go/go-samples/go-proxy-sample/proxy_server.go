// proxy server
// @author: Laba Zhang
package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"strings"
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

	path := r.URL.Path
	log.Println(path)

	var request *http.Request
	if strings.HasPrefix(path, "/a") {
		request, _ = http.NewRequest(r.Method, "http://localhost:8080"+r.URL.Path, r.Body)
	} else if strings.HasPrefix(path, "/b") {
		request, _ = http.NewRequest(r.Method, "http://localhost:8080"+r.URL.Path, r.Body)
	} else {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte("illegal url"))
		return
	}
	response, _ := http.DefaultClient.Do(request)
	defer response.Body.Close()

	// 将实际返回的 Header 及 StatusCode 返回
	w.WriteHeader(response.StatusCode)
	for key, value := range response.Header {
		fmt.Println(key, value[0])
		w.Header().Add(key, value[0])
	}
	fmt.Println(w.Header())
	result, _ := ioutil.ReadAll(response.Body)
	w.Write(result)
}

func main() {
	err := http.ListenAndServe(":2365", &ProxyHandler{})
	if err != nil {
		log.Printf("failed to start server at: %d \n", 2365)
	}
}
