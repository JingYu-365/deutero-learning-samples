// @Description: 查询参数
// @Author: JKong
// @Update: 2020/10/26 7:07 上午
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	r := gin.Default()

	r.GET("/users", func(context *gin.Context) {

		// 如果key不存在，在返回默认值
		pageSize := context.DefaultQuery("pageSize", "10")
		pageNum := context.DefaultQuery("pageNum", "1")
		// 如果不存在key，则返回空字符串
		query := context.Query("query")

		fmt.Printf("query: %s, pageSize: %s, pageNum: %s \n", query, pageSize, pageNum)

		context.JSON(http.StatusOK, gin.H{
			"msg": "OK!",
		})
	})

	err := r.Run(":2365")
	if err != nil {
		fmt.Printf("Start Http Server failed, err:%v \n", err)
	}
}
