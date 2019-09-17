package main

import "gitee.com/jkong/study_go_demo/lesson05/mylogger"

// 使用自定义日志库
func main() {
	logger := mylogger.NewFileLogger("./", "jkong.log")
	msg:="test 123456"

	logger.Debug("log content: %s", msg)
}
