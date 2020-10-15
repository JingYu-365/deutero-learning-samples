package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	router := gin.Default()

	// 处理multipart forms提交文件时默认的内存限制是32 MiB
	// 可以通过下面的方式修改
	// router.MaxMultipartMemory = 8 << 20  // 8 MiB
	router.POST("/file/single", func(c *gin.Context) {
		file, err := c.FormFile("file")
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{
				"message": err.Error(),
			})
			return
		}

		fmt.Println(file.Filename)
		dst := fmt.Sprintf("D:/tmp/%s", file.Filename)
		c.SaveUploadedFile(file, dst)

		c.JSON(http.StatusOK, gin.H{
			"message": fmt.Sprintf("'%s' uploaded!", file.Filename),
		})
	})

	// 支持文件批量上传
	router.POST("/file/batch", func(c *gin.Context) {
		form, _ := c.MultipartForm()

		files := form.File["file"]
		for _, file := range files {
			fmt.Println(file.Filename)
			dst := fmt.Sprintf("D:/tmp/%s", file.Filename)
			c.SaveUploadedFile(file, dst)
		}

		c.JSON(http.StatusOK, gin.H{
			"message": fmt.Sprintf("%d files uploaded!", len(files)),
		})
	})

	router.Run()
}
