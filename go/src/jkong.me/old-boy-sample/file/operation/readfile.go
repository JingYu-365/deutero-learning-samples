package operation

import (
	"bufio"
	"fmt"
	"io"
	"io/ioutil"
	"os"
)

// ReadFileWithSlice 测试使用切片读取文件数据
func ReadFileWithSlice(fileName string) {
	file, err := os.Open(fileName)
	if err != nil {
		fmt.Println("open file field, err: ", err)
		return
	}
	defer file.Close()

	var tmp = make([]byte, 1024)
	n, err := file.Read(tmp)
	if err == io.EOF && n == 0 {
		fmt.Println("read file completed!")
		return
	}
	if err != nil {
		fmt.Printf("Read file field, err: %s \n", err.Error())
		return
	}
	fmt.Printf("file content:\n%s\n", string(tmp[:n]))
}

// ReadFileWithForeach 使用for循环，循环读取文件内容
func ReadFileWithForeach(fileName string) {
	file, err := os.Open(fileName)
	if err != nil {
		fmt.Println("open file field, err: ", err)
		return
	}
	defer file.Close()

	var content []byte
	var tmp = make([]byte, 128)
	for {
		n, err := file.Read(tmp)
		if err == io.EOF && n == 0 {
			fmt.Println("read file completed!")
			break
		}
		if err != nil {
			fmt.Printf("Read file field, err: %s \n", err.Error())
			return
		}
		content = append(content, tmp[:n]...)
	}
	fmt.Printf("file content:\n%s\n", string(content))
}

// ReadFileWithBufIO 使用for循环及缓冲流，循环读取文件内容
func ReadFileWithBufIO(fileName string) {
	file, err := os.Open(fileName)
	if err != nil {
		fmt.Println("open file field, err: ", err)
		return
	}
	defer file.Close()

	reader := bufio.NewReader(file)
	for {
		// 指定每次读取的分割符
		line, err := reader.ReadBytes('\n')
		if err == io.EOF {
			if len(line) != 0 {
				fmt.Println(string(line))
				continue
			} else {
				fmt.Println("read file completed!")
				break
			}
		}
		if err != nil {
			fmt.Printf("Read file field, err: %s \n", err.Error())
			return
		}
	}
}

// ReadAllFileWithIoUtil 使用ioutil读取完整文件
func ReadAllFileWithIoUtil(fileName string) {
	content, err := ioutil.ReadFile(fileName)
	if err != nil {
		fmt.Println("open file field, err: ", err)
		return
	}
	fmt.Println(string(content))
}
