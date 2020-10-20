package logger

import (
	"path"
	"runtime"
)

// 存放公用函数

// 获取行号
func getCallerInfo(skip int) (fileName, funcName string, line int) {
	pc, fileName, line, ok := runtime.Caller(skip)
	if !ok {
		return
	}
	// 从fileName中获取文件名
	// path.Base("/xx/xx/fileName.txt")  ==> fileName.txt
	fileName = path.Base(fileName)

	// 根据 pc 拿到函数名
	funcName = runtime.FuncForPC(pc).Name()
	funcName = path.Base(funcName)
	return fileName, funcName, line
}
