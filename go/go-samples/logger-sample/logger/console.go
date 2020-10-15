package logger

import (
	"fmt"
	"os"
	"time"
)

type ConsoleLogger struct {
	level Level
	file  *os.File
}

// ConsoleLogger 文件日志结构体的构造函数
func NewConsoleLogger(level Level) *ConsoleLogger {

	fl := &ConsoleLogger{
		level: level,
		file:  os.Stdout,
	}
	return fl
}

func (f *ConsoleLogger) IsDebugEnabled() bool {
	return f.isEnabled(DebugLevel)
}

func (f *ConsoleLogger) IsInfoEnabled() bool {
	return f.isEnabled(InfoLevel)
}

func (f *ConsoleLogger) IsWarnEnabled() bool {
	return f.isEnabled(WarningLevel)
}

func (f *ConsoleLogger) IsErrorEnabled() bool {
	return f.isEnabled(ErrorLevel)
}

func (f *ConsoleLogger) IsFatalEnabled() bool {
	return f.isEnabled(FatalLevel)
}

func (f *ConsoleLogger) isEnabled(level Level) bool {
	return f.level <= level
}

// Debug 方法
func (f *ConsoleLogger) Debug(format string, args ...interface{}) {
	f.log(DebugLevel, format, args...)
}

// Info 方法
func (f *ConsoleLogger) Info(format string, args ...interface{}) {
	f.log(InfoLevel, format, args...)
}

// Warn 方法
func (f *ConsoleLogger) Warn(format string, args ...interface{}) {
	f.log(WarningLevel, format, args...)
}

// Error 方法
func (f *ConsoleLogger) Error(format string, args ...interface{}) {
	f.log(ErrorLevel, format, args...)
}

// Fatal 方法
func (f *ConsoleLogger) Fatal(format string, args ...interface{}) {
	f.log(FatalLevel, format, args...)
}

func (f *ConsoleLogger) log(level Level, format string, args ...interface{}) {
	// 1. 如果当前的日志等级大于Debug则不打印
	if !f.isEnabled(level) {
		return
	}

	// 2. 得到用户记录的日志
	msg := fmt.Sprintf(format, args...)

	// 3. 日志格式：[时间][文件：行号][函数名][日志级别] 日志信息
	nowStr := time.Now().Format("2006-01-02 15:04:05.000")
	// getCallerInfo(2): 代表函数的调用层级数
	// 第0层：runtime.Caller(skip)
	// 第1层：getCallerInfo(3)
	// 第2层：调用 Debug(format string, args ...interface{}) 的函数信息
	fileName, funcName, line := getCallerInfo(3)
	logMsg := fmt.Sprintf("[%s][%s:%d][%s][%s] %s \n",
		nowStr, fileName, line, funcName, getLevelStr(level), msg)

	// 3. 利用fmt包将msg字符串写到f.file文件中

	if _, err := fmt.Fprintf(f.file, logMsg); err != nil {
		panic(fmt.Errorf("save log msg error: %v", err))
	}

}

func (f *ConsoleLogger) Close() {
}
