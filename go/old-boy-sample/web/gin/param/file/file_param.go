// @Description: 上传文件
// @Author: JKong
// @Update: 2020/10/26 8:23 下午
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"log"
	"mime/multipart"
	"net/http"
)

func main() {
	r := gin.Default()

	// 单个文件
	r.POST("/ads/file", singleFile)

	// 多个文件
	r.POST("/ads/files", multiFile)

	err := r.Run(":2365")
	if err != nil {
		fmt.Printf("Start Http Server failed, err:%v \n", err)
	}
}

func multiFile(context *gin.Context) {
	form, err := context.MultipartForm()
	if err != nil {
		fmt.Printf("upload files error, err: %v \n", err)
	}

	// 获取所有文件
	files := form.File["files"]
	// 遍历存储文件
	for _, file := range files {
		saveFile(context, file)
	}

	// 返回响应体
	context.String(http.StatusOK, "OK")
}

func singleFile(context *gin.Context) {
	file, err := context.FormFile("file")
	if err != nil {
		fmt.Printf("upload file error, err: %v \v", err)
	}
	saveFile(context, file)
	context.String(http.StatusOK, "OK")
}

func saveFile(context *gin.Context, file *multipart.FileHeader) {
	// 输出文件名称及文件大小
	log.Printf("file name is: %s , file size is: %v \n", file.Filename, file.Size)

	// 将文件保存在项目根目录下
	err := context.SaveUploadedFile(file, file.Filename)
	if err != nil {
		fmt.Printf("save file error, err: %v \n", err)
	}
}
