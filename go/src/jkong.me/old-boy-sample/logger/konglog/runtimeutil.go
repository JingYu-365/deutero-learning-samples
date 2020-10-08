package konglog

import (
	"fmt"
	"path"
	"runtime"
)

// GetfuncRunTimeInfo 获取函数信息
func GetfuncRunTimeInfo(skip int) (filename, funcName string, lineNo int) {
	pc, fullFileName, lineNo, ok := runtime.Caller(skip)
	if !ok {
		fmt.Println("runtime caller() execute failed!")
	}
	filename = path.Base(fullFileName)
	funcName = runtime.FuncForPC(pc).Name()
	return filename, funcName, lineNo
}
