package konglog

import (
	"fmt"
	"time"
)

// Logger 日志结构体
type Logger struct {
	Level LogLevel
}

// NewLogger 创建日志对象
func NewLogger(logLevel string) Logger {
	level, err := parseLogLevel(logLevel)
	if err != nil {
		panic(err)
	}
	return Logger{Level: level}
}

// Debug 输出 Debug 等级日志
func (log *Logger) Debug(msg string) {
	log.log(DEBUG, msg)
}

// Trace 输出 Trace 等级日志
func (log *Logger) Trace(msg string) {
	log.log(TRACE, msg)
}

// Info 输出 Info 等级日志
func (log *Logger) Info(msg string) {
	log.log(INFO, msg)
}

// Warnning 输出 Warnning 等级日志
func (log *Logger) Warnning(msg string) {
	log.log(WARNING, msg)
}

// Error 输出 Error 等级日志
func (log *Logger) Error(msg string) {
	log.log(ERROR, msg)
}

// Fatal 输出 Fatal 等级日志
func (log *Logger) Fatal(msg string) {
	log.log(FATAL, msg)
}

func (log *Logger) log(level LogLevel, msg string) {
	if level >= log.Level {
		filename, funcName, lineNo := GetfuncRunTimeInfo(3)
		fmt.Printf("[%s] [%s:%s:%d] [%s] %s \n",
			// 时间
			time.Now().Format("2006-01-02 15:04:05"),
			// 文件名：函数名：行号
			filename, funcName, lineNo,
			// loglevel
			parseLogLevelForString(level),
			// 日志信息
			msg)
	}
}
