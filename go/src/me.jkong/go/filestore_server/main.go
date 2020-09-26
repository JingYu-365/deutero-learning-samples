package main

import (
	"fmt"
	"jkong.me/jkong/filestore_server/handler"
	"net/http"
)

func main() {
	http.HandleFunc("/file/upload", handler.UploadHandler)
	http.HandleFunc("/file/upload/suc", handler.UploadSucHandler)
	http.HandleFunc("/file/meta", handler.GetFileMetaHandler)
	http.HandleFunc("/file/download", handler.DownloadFileHandler)
	http.HandleFunc("/file/meta/modification", handler.UpdateFileMetaHandler)
	http.HandleFunc("/file/delete", handler.DeleteFileMetaHandler)

	// user
	http.HandleFunc("/user/signup", handler.UserSignUpHandler)
	http.HandleFunc("/user/signin", handler.UserSignInHandler)

	// 配置静态文件位置
	fsh := http.FileServer(http.Dir("/Users/jdkong/Documents/github/personal-samples/go/src/jkong.me/jkong/filestore_server/static"))
	http.Handle("/static/", http.StripPrefix("/static/", fsh))
	err := http.ListenAndServe(":8080", nil)
	if err != nil {
		fmt.Printf("failed to start server, err:%s", err.Error())
	}

}
