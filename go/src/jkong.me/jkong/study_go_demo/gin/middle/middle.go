package main

import (
    "fmt"
    "github.com/gin-gonic/gin"
    "net/http"
    "time"
)

func main() {
    r := gin.Default()

    // 注册中间件
    r.Use(StatCost())

    r.GET("/users", func(c *gin.Context) {
        // 休眠
        //time.Sleep(time.Duration(2) * time.Second)
        c.JSON(http.StatusOK, gin.H{"msg": "this is a middleware test."})

    })
    r.Run()
}

// StatCost: 是一个统计请求耗时的中间件
func StatCost() gin.HandlerFunc {
    return func(c *gin.Context) {
        start := time.Now()
        c.Set("name", "JKong")
        c.Next()
        cost := time.Since(start)
        fmt.Printf("request cost time: %d \n", cost.Nanoseconds())
    }
}
