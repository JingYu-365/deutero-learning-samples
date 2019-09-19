package main

import (
    "fmt"
    "github.com/gin-gonic/gin"
    "net/http"
)

func main() {
    r := gin.Default()

    // 绑定json示例 {user:"JKONG", password:"123456"}
    r.POST("/login/json", func(c *gin.Context) {
        var login Login

        if err := c.ShouldBindJSON(&login); err == nil {
            fmt.Printf("login info: %#v \n", login)
            c.JSON(http.StatusOK, gin.H{
                "user":     login.User,
                "password": login.Password,
            })
        } else {
            c.JSON(http.StatusBadRequest, gin.H{
                "error": err.Error(),
            })
        }

    })

    // 绑定form表单示例 (user=q1mi&password=123456)
    r.POST("/login/form", func(c *gin.Context) {
        var login Login

        if err := c.ShouldBind(&login); err == nil {
            fmt.Printf("login info: %#v \n", login)
            c.JSON(http.StatusOK, gin.H{
                "user":     login.User,
                "password": login.Password,
            })
        } else {
            c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
        }

    })

    // 绑定query string
    r.GET("/login/query", func(c *gin.Context) {
        var login Login

        // ShouldBind()会根据请求的Content-Type自行选择绑定器
        if err := c.ShouldBind(&login); err == nil {
            fmt.Printf("login info: %#v \n", login)
            c.JSON(http.StatusOK, gin.H{
                "user":     login.User,
                "password": login.Password,
            })
        } else {
            c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
        }
    })


    r.Run()
}

// Binding from JSON
type Login struct {
    User     string `form:"user" json:"user" binding:"required"`
    Password string `form:"password" json:"password" binding:"required"`
}
