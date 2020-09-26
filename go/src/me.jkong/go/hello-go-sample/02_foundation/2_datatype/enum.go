// @Description: Go 中没有特定定义 Enum 的方式， 可以通过 const 方式来定义Enum
// @Author: JKong
// @Update: 2020/9/26 9:01 下午
package main

import "fmt"

const (
	java       = "JAVA"
	python     = "PYTHON"
	javascript = "JAVASCRIPT"
	cpp        = "CPP"
)

func main() {
	fmt.Println(java, python, javascript, cpp)
}
