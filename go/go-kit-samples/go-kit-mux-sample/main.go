// @Description: 程序主入口
// @Author: JKong
// @Update: 2020/10/31 11:01 上午
package main

import (
	"fmt"
	kithttp "github.com/go-kit/kit/transport/http"
	"github.com/gorilla/mux"
	"labazhang.me/config"
	"labazhang.me/services"
	"log"
	"net/http"
)

func main() {
	userService := services.UserService{}
	userEndPoint := services.GenerateUserEndpoint(userService)

	userHandler := kithttp.NewServer(userEndPoint, services.DecodeUserRequest, services.EncodeUserResponse)

	// 使用 路有插件进行路由
	router := mux.NewRouter()
	//router.Handle("/users/{uid:\\d+}", userHandler)
	// 定义一个只允许Get请求的路由
	router.Methods("GET", "DELETE").Path("/users/{uid:\\d+}").Handler(userHandler)

	router.Methods("GET").Path("/health").HandlerFunc(func(writer http.ResponseWriter, request *http.Request) {
		writer.Header().Add("Content-Type", "application/json")
		_, _ = writer.Write([]byte(`{"status":"OK"}`))
	})

	// 启动服务
	log.Printf("server start at %d \n", 2365)
	err := http.ListenAndServe(fmt.Sprintf(":%d", config.LocalPort), router)
	if err != nil {
		log.Fatalf("failed to start server, err: %v \n", err)
	}

}
