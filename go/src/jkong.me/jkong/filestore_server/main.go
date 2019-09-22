package main

import (
	"fmt"
	"jkong.me/jkong/filestore_server/handler"
	"net/http"
)

func main() {
	http.HandleFunc("/file/upload", handler.UploadHandler)
	http.HandleFunc("/file/upload/suc", handler.UploadSucHandler)
	http.HandleFunc("/file/meta",handler.GetFileMetaHandler)
	http.HandleFunc("/file/query",handler.FileQueryHandler)
	http.HandleFunc("/file/download",handler.DownloadFileHandler)

	err := http.ListenAndServe(":8080", nil)
	if err!= nil {
		fmt.Printf("failed to start server, err:%s", err.Error())
	}

}
