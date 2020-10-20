// @Description: 使用defer关闭资源
// @Author: JKong
// @Update: 2020/10/3 5:29 下午
package main

import (
	"bufio"
	"fmt"
	"os"
)

// defer 是在方法return 之前执行。
// defer 执行的顺序为倒序，先进后出（栈结构）
func writeFile(fileName string) {
	var file *os.File
	var err error

	// 打开文件
	file, err = os.OpenFile(fileName, os.O_WRONLY|os.O_CREATE, 0666)
	if err != nil {
		// todo 特定 error 处理方式
		if _, ok := err.(*os.PathError); !ok {
			panic(err)
		} else {
			file, err = os.Create(fileName)
		}
	}
	// 关闭文件
	defer file.Close()

	// 使用缓冲区
	writer := bufio.NewWriter(file)

	count, err := writer.WriteString("this is a defer demo!!!")
	defer writer.Flush()
	// 写出数据
	if err != nil {
		panic(err)
	}
	fmt.Println(count)
}

func main() {
	writeFile("defer.txt")
}
