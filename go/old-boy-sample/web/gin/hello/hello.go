// @Description: Hello Gin
// @Author: JKong
// @Update: 2020/10/26 6:45 上午
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	// 创建路由
	r := gin.Default()

	// 绑定路由规则，执行函数, gin.Context封装了Request和Response
	r.GET("/hello", func(context *gin.Context) {
		context.JSON(http.StatusOK, gin.H{
			"msg": "Hello Gin!",
		})
	})

	// 绑定端口并启动服务（注意：此处端口需要以‘:’开头）
	err := r.Run(":2365")
	if err != nil {
		fmt.Printf("Start Http Server failed, err:%v \n", err)
	}
	// 测试：curl -GET http://127.0.0.1:2365/hello
}
