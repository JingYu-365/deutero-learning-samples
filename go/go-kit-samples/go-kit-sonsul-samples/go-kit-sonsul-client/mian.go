// @Description: consul client 入口程序
// @Author: la ba zhang
// @Update: 2020/11/1 9:52 上午
package main

import (
	"context"
	kithttp "github.com/go-kit/kit/transport/http"
	. "labazhang.me/services"
	"log"
	"net/url"
)

func main() {
	targetUrl, _ := url.Parse("http://127.0.0.1:2365")
	client := kithttp.NewClient("GET", targetUrl, EncodeGetUserInfoReq, DecodeGetUserInfoResp)
	clientEndpoint := client.Endpoint()
	userInfo, err := clientEndpoint(context.Background(), UserRequest{Uid: 1}) // admin
	//userInfo, err := clientEndpoint(context.Background(), UserRequest{Uid: 2}) // guest
	if err != nil {
		log.Fatal(err)
	}
	log.Println(userInfo.(UserResponse).Result)
}
