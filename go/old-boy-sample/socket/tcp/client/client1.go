package main

import (
	"bufio"
	"fmt"
	"net"
	"os"
	"strings"
)

// TCP客户端进行TCP通信的流程如下：
// - 建立与服务端的链接
// - 进行数据收发
// - 关闭链接
func main() {
	var address = "127.0.0.1:2365"
	conn, err := net.Dial("tcp", "127.0.0.1:2365")
	if err != nil {
		fmt.Printf("client connect server error, err: %v \n", err)
		return
	}
	defer conn.Close()
	fmt.Printf("connect %s success! \n", address)

	// 读取控制台输入
	reader := bufio.NewReader(os.Stdout)
	for {
		// 读取用户输入
		input, _ := reader.ReadString('\n')
		inputInfo := strings.Trim(input, "\r\n")
		// 如果输入q就退出
		if strings.ToLower(inputInfo) == "exit" {
			return
		}
		_, err := conn.Write([]byte(inputInfo))
		if err != nil {
			return
		}

		var buf [128]byte
		n, err := conn.Read(buf[:])
		if err != nil {
			fmt.Printf("client read data error, err: %v \n", err)
		}
		fmt.Printf("reveive server sent data：%v \n", string(buf[:n]))
	}

}
