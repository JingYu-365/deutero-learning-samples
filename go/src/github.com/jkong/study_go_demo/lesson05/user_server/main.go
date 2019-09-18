package main

import (
	"github.com/jkong/study_go_demo/lesson05/logger"
)

// 面向接口编程
var log logger.Logger

// 使用自定义日志库
func main() {
	//logger := logger.NewFileLogger("./", "jkong.log", logger.ErrorLevel)
	defer log.Close()
	log = logger.NewConsoleLogger(logger.InfoLevel)
	msg := "test 123456"

	if log.IsDebugEnabled() {
		log.Debug("debug log content: %s", msg)
	}

	if log.IsInfoEnabled() {
		log.Info("info log content: %s", msg)
	}

	if log.IsErrorEnabled() {
		log.Error("error log content: %s", msg)
	}
}
