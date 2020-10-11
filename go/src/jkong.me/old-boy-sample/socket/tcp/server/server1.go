package main

import (
	"bufio"
	"fmt"
	"net"
)

// TCP服务端程序的处理流程：
// - 监听端口
// - 接收客户端请求建立链接
// - 创建goroutine处理链接。
// 		- 读取客户端发送的数据
// 		- 向客户端写数据
func main() {
	var address = "127.0.0.1:2365"
	listen, err := net.Listen("tcp", address)
	if err != nil {
		fmt.Printf("server listen {%s} field, err: %v \n", address, err)
		return
	}

	for {
		// 循环获取连接
		conn, err := listen.Accept()
		if err != nil {
			fmt.Printf("accept filed, err: %v \n", err)
			continue
		}
		// 启用协程处理连接信息
		go processConn(conn)
	}

}

// 处理连接
func processConn(conn net.Conn) {
	defer conn.Close()
	for {
		reader := bufio.NewReader(conn)
		var buf [126]byte
		// 读取数据
		n, err := reader.Read(buf[:])
		if err != nil {
			fmt.Printf("read from client failed, err: %v \n", err)
			break
		}
		recvStr := string(buf[:n])
		fmt.Println("reveive client sent data：", recvStr)
		// 发送响应数据
		conn.Write([]byte("received"))
	}
}
