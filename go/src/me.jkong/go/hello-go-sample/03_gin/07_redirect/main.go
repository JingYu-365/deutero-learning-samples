package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	router := gin.Default()

	// HTTP重定向
	router.GET("/index", func(c *gin.Context) {
		c.Redirect(http.StatusMovedPermanently, "http://www.google.com/")
	})

	// 路由重定向
	router.GET("/router/original", func(c *gin.Context) {
		c.Request.URL.Path = "/router/target"
		router.HandleContext(c)
	})

	router.GET("/router/target", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"msg": "this is router redirect!"})
	})

	router.Run()
}
