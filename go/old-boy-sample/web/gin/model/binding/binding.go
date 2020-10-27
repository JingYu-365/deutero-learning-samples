// @Description: 结构体参数绑定
// @Author: JKong
// @Update: 2020/10/27 6:58 上午
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"web/gin/model"
)

func main() {
	router := gin.Default()

	login := router.Group("/login")
	{
		login.POST("/json", loginJson)
		login.POST("/xml", loginXml)
		login.POST("/form", loginForm)
	}

	query := router.Group("/query")
	{
		query.GET("/user", onlyQuery)
	}

	queryPost := router.Group("/queryOrPost")
	{
		queryPost.PUT("/users", queryOrPost)
	}

	err := router.Run(":2365")
	if err != nil {
		fmt.Printf("Start Http Server failed, err:%v \n", err)
	}
}

func queryOrPost(context *gin.Context) {
	var person model.Person
	// If `GET`, only `Form` binding engine (`query`) used.
	// If `POST`, first checks the `content-type` for `JSON` or `XML`, then uses `Form` (`form-data`).
	if err := context.ShouldBind(&person); err == nil {
		fmt.Println(person.Name)
		fmt.Println(person.Address)
		fmt.Println(person.Birthday)
		fmt.Println(person.CreateTime)
	} else {
		fmt.Printf("error: %v \n", err)
	}

	context.String(200, "Success")
}

func onlyQuery(context *gin.Context) {
	var person model.Person
	if context.ShouldBindQuery(&person) == nil {
		log.Println("====== Only Bind By Query String ======")
		log.Println(person.Name)
		log.Println(person.Address)
	}
	context.String(http.StatusOK, "Success")
}

var (
	defaultUserName = "kong"
	defaultPassword = "123456"
)

func processLoginInfo(user model.User, context *gin.Context) {
	fmt.Printf("user info: username: %s, password: %s \n", user.Username, user.Password)
	if user.Username != defaultUserName || user.Password != defaultPassword {
		context.JSON(http.StatusUnauthorized, gin.H{"status": "unauthorized"})
		return
	}

	context.JSON(http.StatusOK, gin.H{"status": "you are logged in"})
}

// Example for binding a HTML form (username=kong&password=123456)
func loginForm(context *gin.Context) {
	var user model.User
	if err := context.ShouldBind(&user); err != nil {
		context.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	processLoginInfo(user, context)
}

// Example for binding XML (
//	<?xml version="1.0" encoding="UTF-8"?>
//	<root>
//		<username>kong</username>
//		<password>123456</password>
//	</root>)
func loginXml(context *gin.Context) {
	var user model.User
	if err := context.ShouldBindXML(&user); err != nil {
		context.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	processLoginInfo(user, context)
}

// Example for binding JSON ({"username": "kong", "password": "123456"})
func loginJson(context *gin.Context) {
	var user model.User
	if err := context.ShouldBindJSON(&user); err != nil {
		context.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	processLoginInfo(user, context)
}
