package main

import (
	"fmt"
	"path"
	"runtime"
)

func main() {
	fmt.Println("====== Caller(0)")
	getInfo(0)
	fmt.Println("====== Caller(1)")
	getInfo(1)
}

func getInfo(n int) {
	// Caller 中传递int型数值，代表寻找第几层的调用者
	// 0 表示 runtime#Caller 所在执行的方法的信息，例如：当前的 getInfo 方法
	// 1 表示 调用 runtime#Caller 所在方法的方法的信息，例如当前的 main 方法
	// 2 …… 依上类推
	pc, fullFileName, line, ok := runtime.Caller(n)
	if !ok {
		fmt.Println("runtime caller() execute failed!")
	}
	// 函数所在文件的名称
	fmt.Println(fullFileName)
	fmt.Println(path.Base(fullFileName))
	// 函数被调用的行数
	fmt.Println(line)
	// 获取函数信息
	function := runtime.FuncForPC(pc)
	// 输出函数名称
	fmt.Println(function.Name())
}
