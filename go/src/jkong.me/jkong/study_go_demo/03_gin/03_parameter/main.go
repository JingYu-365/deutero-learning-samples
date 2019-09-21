package main

import (
    "encoding/json"
    "fmt"
    "github.com/gin-gonic/gin"
    "io/ioutil"
    "net/http"
)

func main() {
    r := gin.Default()

    // 获取query中参数
    r.GET("/users", func(context *gin.Context) {
        username := context.DefaultQuery("username", "jkong")
        age := context.DefaultQuery("age", "25")

        fmt.Printf("username: %s, age: %s \n", username, age)

        context.JSON(http.StatusOK, gin.H{
            "message":  "ok",
            "username": username,
            "age":      age,
        })
    })

    // 获取 PostForm 中参数
    r.POST("/users", func(context *gin.Context) {
        username := context.PostForm("username")
        age := context.PostForm("age")
        fmt.Printf("username: %s, age: %s \n", username, age)

        context.JSON(http.StatusOK, gin.H{
            "message":  "ok",
            "username": username,
            "age":      age,
        })
    })

    // 获取 Post Body 中参数
    r.POST("/user", func(context *gin.Context) {
        body, _ := ioutil.ReadAll(context.Request.Body)
        if body != nil {
            fmt.Printf("请求body内容为:%s \n", body)
        }

        // 使用 json 获取格式化数据
        user := &User{}
        json.Unmarshal([]byte(body), user)

        context.JSON(http.StatusOK, gin.H{
            "message":  "ok",
            "username": user.Username,
            "age":      user.Age,
        })
    })

    // 获取 path 中参数
    r.GET("/users/:username/:age", func(c *gin.Context) {
        username := c.Param("username")
        age := c.Param("age")

        fmt.Printf("username: %s, age: %s \n", username, age)

        c.JSON(http.StatusOK, gin.H{
            "message":  "ok",
            "username": username,
            "age":      age,
        })
    })

    r.Run()
}

type User struct {
    Username string `json:"username"`
    Age      int    `json:"age"`
}
