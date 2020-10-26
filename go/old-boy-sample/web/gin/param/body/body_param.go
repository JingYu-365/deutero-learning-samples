// @Description: 请求体参数
// @Author: JKong
// @Update: 2020/10/26 7:06 上午
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	r := gin.Default()

	// 绑定路由规则，执行函数, gin.Context封装了Request和Response
	r.GET("/users", func(context *gin.Context) {

		context.JSON(http.StatusOK, gin.H{
			"msg": "Hello Gin!",
		})
	})

	// 绑定端口并启动服务（注意：此处端口需要以‘:’开头）
	err := r.Run(":2365")
	if err != nil {
		fmt.Printf("Start Http Server failed, err:%v \n", err)
	}
}
