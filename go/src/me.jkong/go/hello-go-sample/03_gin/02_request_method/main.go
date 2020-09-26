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

	r.Any("/any", func(context *gin.Context) {
		context.JSON(http.StatusOK, gin.H{"msg": "ang request method"})
	})

	r.NoRoute(func(context *gin.Context) {
		context.JSON(http.StatusNotFound, gin.H{"msg": "api not found!"})
	})

	r.Run(":8081")
}
