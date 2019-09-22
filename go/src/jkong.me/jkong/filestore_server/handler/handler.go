package handler

import (
	"encoding/json"
	"fmt"
	"jkong.me/jkong/filestore_server/meta"
	"jkong.me/jkong/filestore_server/util"
	"io"
	"io/ioutil"
	"net/http"
	"os"
	"strconv"
	"time"
)

// 处理文件上传
func UploadHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == "GET" {
		// 返回上传页面html
		data, err := ioutil.ReadFile("src/github.com/jkong/filestore_server/static/view/index.html")
		if err != nil {
			fmt.Printf("open index.html error, err: %s", err.Error())
			_, _ = io.WriteString(w, "internal server error!")
		}
		_, _ = io.WriteString(w, string(data))
	} else if r.Method == "POST" {
		// 接收文件流，将文件当到指定位置
		file, header, err := r.FormFile("file")
		if err != nil {
			fmt.Printf("failed to read file form reqeust!")
			return
		}
		defer file.Close()

		fileMeta := meta.FileMeta{
			FileName: header.Filename,
			Location: fmt.Sprintf("/tmp/jkong/%s", header.Filename),
			UploadAt: time.Now().Format("2006-01-02 15:04:05"),
		}

		newFile, err := os.Create(fileMeta.Location)
		if err != nil {
			fmt.Printf("failed to create file error, err: %s", err.Error())
			return
		}
		defer newFile.Close()

		fileMeta.FileSize, err = io.Copy(newFile, file)
		if err != nil {
			fmt.Printf("failed to save file, err: %s", err.Error())
			return
		}
		// 计算文件的sha1
		newFile.Seek(0,0)
		fileMeta.FileSha1 = util.FileSha1(newFile)

		// 保存文件元信息
		//meta.UpdateFileMeta(fileMeta)
		meta.UpdateFileMetaDB(fileMeta)

		http.Redirect(w, r, "/file/upload/suc", http.StatusFound)
	}
}

// 上传完成
func UploadSucHandler(w http.ResponseWriter, r *http.Request) {
	io.WriteString(w, "Upload finished!")
}

// 获取文件元信息
func GetFileMetaHandler(w http.ResponseWriter, r *http.Request)  {
	// 解析表单参数
	r.ParseForm()

	// 获取表单提交数据
	fileHash := r.Form["fileHash"][0]
	// r.FormValue("fileHash")
	// r.Form.Get("fileHash")

	// 转为Json
	//fileMeta := meta.GetFileMeta(fileHash)
	fileMeta, err := meta.GetFileMetaDB(fileHash)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	data, err := json.Marshal(fileMeta)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	w.Write(data)
}

// 查询
func FileQueryHandler(w http.ResponseWriter, r *http.Request)  {
	err := r.ParseForm()
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	limitNum, err := strconv.Atoi(r.FormValue("limit"))
	fmt.Println(limitNum)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	w.WriteHeader(http.StatusOK)
}

// 下载文件
func DownloadFileHandler(w http.ResponseWriter, r *http.Request)  {
	r.ParseForm()
	fileHash := r.FormValue("fileHash")
	fileMeta := meta.GetFileMeta(fileHash)

	file, err := os.Open(fileMeta.Location)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	defer file.Close()

	// 读取文件
	data, err := ioutil.ReadAll(file)
	if err!= nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type","application/octect-stream")
	w.Header().Set("content-disposition","attachment;filename=\""+fileMeta.FileName+"\"")
	w.Write(data)
}

// 文件元信息更新
func UpdateFileMetaHandler(w http.ResponseWriter, r *http.Request)  {
	r.ParseForm()
	fileHash := r.FormValue("fileHash")
	fileName := r.FormValue("fileName")

	if r.Method != "PUT" {
		w.WriteHeader(http.StatusMethodNotAllowed)
		return
	}

	fileMeta := meta.GetFileMeta(fileHash)
	fileMeta.FileName = fileName
	meta.UpdateFileMeta(fileMeta)

	w.WriteHeader(http.StatusOK)
	data, err := json.Marshal(fileMeta)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	w.Write(data)
}

// 删除文件信息
func DeleteFileMetaHandler(w http.ResponseWriter, r *http.Request)  {
	r.ParseForm()

	fileHash := r.FormValue("fileHash")

	// 删除文件
	fileMeta := meta.GetFileMeta(fileHash)
	os.Remove(fileMeta.Location)
	meta.DeleteFileMeta(fileHash)

	w.WriteHeader(http.StatusOK)
}