// @Description: 面向接口
// @Author: JKong
// @Update: 2020/10/2 8:08 下午
package main

import (
	"fmt"
	real2 "jkong.me/hello-go-sample/05_oo_interface/real"
)

// 定义接口
type Retriever interface {
	Get(url string) string
}

// 使用接口中定义的方法
func download(r Retriever, url string) string {
	return r.Get(url)
}

type Poster interface {
	Post(url string, form map[string]string) string
}

func post(p Poster) {
	p.Post("http://www.jkong.me", map[string]string{
		"name": "jkong",
		"pwd":  "123456",
	})
}

// 接口组合 RetrieverAndPoster 中组合 	Retriever & Poster 接口
// 在 RetrieverAndPoster 还可以继续定义其他接口
type RetrieverAndPoster interface {
	Retriever
	Poster
}

func session(rp RetrieverAndPoster) {
	rp.Post("http://www.jkong.me", map[string]string{
		"name": "jkong",
		"pwd":  "123456",
	})

	rp.Get("http://www.jkong.me")
}

func main() {
	// 构建接口实现对象
	r := real2.Retriever{UserAgent: "PostMan"}
	// 执行
	//fmt.Println(download(r, "http://www.github.com"))

	// 如何知道 r 的真实类型和内容呢?
	// 方法一：type assertion
	if retriever, ok := interface{}(r).(real2.Retriever); ok {
		fmt.Println(retriever.UserAgent)
	}

	// 方法二：type switch
	switch v := interface{}(r).(type) {
	case real2.Retriever:
		fmt.Println("UserAgent:", v.UserAgent)
	}
}
