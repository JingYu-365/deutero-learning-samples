// @Description: 程序主入口
// @Author: JKong
// @Update: 2020/10/31 11:01 上午
package main

import (
	kithttp "github.com/go-kit/kit/transport/http"
	"github.com/gorilla/mux"
	"labazhang.me/service"
	"log"
	"net/http"
)

func main() {
	userService := service.UserService{}
	userEndPoint := service.GenerateUserEndpoint(userService)

	userHandler := kithttp.NewServer(userEndPoint, service.DecodeUserRequest, service.EncodeUserResponse)

	// 使用 路有插件进行路由
	router := mux.NewRouter()
	router.Handle("/users/{uid:\\d+}", userHandler)

	log.Printf("server start at %d \n", 2365)
	err := http.ListenAndServe(":2365", router)
	if err != nil {
		log.Fatalf("failed to start server, err: %v \n", err)
	}
}
