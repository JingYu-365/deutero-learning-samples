package golog

import (
	"fmt"
	"log"
	"os"
	"time"
)

// TestGoLog 测试go语言自带log
func TestGoLog() {
	file, err := os.OpenFile("./test.log", os.O_CREATE|os.O_APPEND|os.O_WRONLY, 0666)
	if err != nil {
		fmt.Printf("open file field, err: %s \n", err.Error())
		return
	}

	// 向控制台输出日志
	log.SetOutput(os.Stdout)
	for i := 0; i < 5; i++ {
		log.Println("这是测试日志")
		time.Sleep(time.Second * 3)
	}

	// 向自定义文件输出日志
	log.SetOutput(file)
	for i := 0; i < 5; i++ {
		log.Println("这是测试日志")
		time.Sleep(time.Second * 3)
	}
}
