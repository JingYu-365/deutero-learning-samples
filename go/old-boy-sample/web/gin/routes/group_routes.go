// @Description: TODO
// @Author: JKong
// @Update: 2020/10/26 9:02 下午
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	router := gin.Default()

	// Simple group: v1
	v1 := router.Group("/v1")
	{
		v1.POST("/login", loginEndpoint)
		v1.POST("/submit", submitEndpoint)
		v1.POST("/read", readEndpoint)
	}

	// Simple group: v2
	v2 := router.Group("/v2")
	{
		v2.POST("/login", loginEndpoint)
		v2.POST("/submit", submitEndpoint)
		v2.POST("/read", readEndpoint)
	}

	router.Run(":2365")

	// 测试：
	// curl -X POST http://127.0.0.1:2365/v2/login
	// curl -X POST http://127.0.0.1:2365/v1/login
}

func readEndpoint(context *gin.Context) {
	result(context)
}

func submitEndpoint(context *gin.Context) {
	result(context)
}

func loginEndpoint(context *gin.Context) {
	result(context)
}

func result(context *gin.Context) {
	fmt.Println(context.Request.RequestURI)
	context.String(http.StatusOK, "OK")
}
