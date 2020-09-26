package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	router := gin.Default()

	userGroup := router.Group("/users")
	{
		userGroup.GET("", func(c *gin.Context) {
			c.JSON(http.StatusOK, gin.H{"msg": "get user information."})
		})

		userGroup.POST("", func(c *gin.Context) {
			c.JSON(http.StatusOK, gin.H{"msg": "add user information."})
		})
	}

	roleGroup := router.Group("/roles")
	{
		roleGroup.GET("", func(c *gin.Context) {
			c.JSON(http.StatusOK, gin.H{"msg": "get role information."})
		})

		roleGroup.POST("", func(c *gin.Context) {
			c.JSON(http.StatusOK, gin.H{"msg": "add role information."})
		})
	}

	router.Run()
}
