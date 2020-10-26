// @Description: 路径参数Demo
// @Author: JKong
// @Update: 2020/10/26 7:03 上午
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	r := gin.Default()
	r.GET("/users/:name", func(context *gin.Context) {
		name := context.Param("name")
		fmt.Println("param name:", name)

		context.String(http.StatusOK, "OK")
	})

	err := r.Run(":2365")
	if err != nil {
		fmt.Printf("Start Http Server failed, err:%v \n", err)
	}
	// 测试：curl -GET http://127.0.0.1:2365/users/zhangsan
}
