package konglog

import (
	"fmt"
	"os"
)

// GetFileSize 获取文件大小
func GetFileSize(file *os.File) int64 {
	fileInfo, err := file.Stat()
	if err != nil {
		fmt.Printf("get log file size error, err: %v \n", err)
	}
	return fileInfo.Size()
}

// SliceFile 切割文件
func SliceFile(file *os.File, bakName string) *os.File {
	filename := file.Name()
	fmt.Println(filename)
	// 关闭当前文件
	file.Close()
	// 备份旧文件
	os.Rename(filename, bakName)
	// 打开新文件
	newFile, err := os.OpenFile(filename, os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0644)
	if err != nil {
		panic(err)
	}
	return newFile
}
