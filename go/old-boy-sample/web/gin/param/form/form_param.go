// @Description: 获取表单数据
// @Author: JKong
// @Update: 2020/10/26 8:09 下午
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	r := gin.Default()

	r.POST("/user/login", func(context *gin.Context) {

		role := context.DefaultPostForm("role", "normal")
		username := context.PostForm("username")
		password := context.PostForm("password")

		context.String(http.StatusOK, fmt.Sprintf("role: %s, username: %s, password: %s", role, username, password))
	})

	err := r.Run(":2365")
	if err != nil {
		fmt.Printf("Start Http Server failed, err:%v \n", err)
	}
}
