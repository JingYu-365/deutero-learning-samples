package main

import (
    "github.com/gin-gonic/gin"
    "net/http"
)

func main() {
    r := gin.Default()

    r.GET("/books", func(context *gin.Context) {
        context.JSON(http.StatusOK, gin.H{
            "msg": "GET",
        })
    })

    r.POST("/books", func(context *gin.Context) {
        context.JSON(http.StatusOK, gin.H{
            "msg": "POST",
        })
    })

    r.DELETE("/books", func(context *gin.Context) {
        context.JSON(http.StatusOK, gin.H{
            "msg": "DELETE",
        })
    })

    r.PUT("/books", func(context *gin.Context) {
        context.JSON(http.StatusOK, gin.H{
            "msg": "PUT",
        })
    })

    r.Run(":8081")
}
