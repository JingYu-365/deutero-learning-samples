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
	"labazhang.me/util"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
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

	// 注册服务并启动服务
	serverErrCh := make(chan error)
	go func() {
		util.ServiceRegister()
		log.Printf("server start at %d \n", 2365)
		err := http.ListenAndServe(fmt.Sprintf(":%d", config.LocalPort), router)
		if err != nil {
			log.Fatalf("failed to start server, err: %v \n", err)
			serverErrCh <- err
		}
	}()

	go func() {
		signalCh := make(chan os.Signal)
		signal.Notify(signalCh, syscall.SIGINT, syscall.SIGTERM)
		serverErrCh <- fmt.Errorf("%s", <-signalCh)
	}()

	select {
	case errInfo := <-serverErrCh:
		fmt.Println(errInfo)
		util.ServiceDeRegister()
	}
}
