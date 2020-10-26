// @Description: 中间件测试
// @Author: JKong
// @Update: 2020/10/26 9:10 下午
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
)

func main() {
	// 创建一个不带中间件的Router
	r := gin.New()

	// Global middleware
	// Logger middleware will write the logs to gin.DefaultWriter even if you set with GIN_MODE=release.
	// By default gin.DefaultWriter = os.Stdout
	r.Use(gin.Logger())

	// Recovery middleware recovers from any panics and writes a 500 if there was one.
	r.Use(gin.Recovery())

	// Per route middleware, you can add as many as you desire.
	r.GET("/benchmark", MyBenchLogger(), MyBenchLogger2(), benchEndpoint)

	// Authorization group
	// authorized := r.Group("/", AuthRequired())
	// exactly the same as:
	authorized := r.Group("/")
	// per group middleware! in this case we use the custom created
	// AuthRequired() middleware just in the "authorized" group.
	authorized.Use(AuthRequired())
	{
		authorized.POST("/login", loginEndpoint)
		authorized.POST("/submit", submitEndpoint)
		authorized.POST("/read", readEndpoint)

		// nested group
		testing := authorized.Group("testing")
		testing.GET("/analytics", analyticsEndpoint)
	}

	// Listen and serve on 0.0.0.0:8080
	err := r.Run(":8080")
	if err != nil {
		fmt.Printf("Start Http Server failed, err:%v \n", err)
	}
}

func analyticsEndpoint(context *gin.Context) {

}

func readEndpoint(context *gin.Context) {

}

func submitEndpoint(context *gin.Context) {

}

func loginEndpoint(context *gin.Context) {

}

func benchEndpoint(context *gin.Context) {

}

func AuthRequired() gin.HandlerFunc {
	return func(context *gin.Context) {

		context.Next()
	}
}

func MyBenchLogger2() gin.HandlerFunc {
	return func(context *gin.Context) {

		context.Next()
	}
}

func MyBenchLogger() gin.HandlerFunc {
	return func(context *gin.Context) {

		context.Next()
	}
}
