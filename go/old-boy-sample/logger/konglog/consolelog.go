package konglog

import (
	"fmt"
	"time"
)

// ConsoleLogger 控制台输出
type ConsoleLogger struct {
	Logger Logger
}

func newConsoleLogger(logLevel LogLevel) *ConsoleLogger {
	return &ConsoleLogger{Logger: Logger{Level: logLevel}}
}

// Debug 输出 Debug 等级日志
func (log *ConsoleLogger) Debug(msg string, a ...interface{}) {
	log.log(DEBUG, msg, a)
}

// Trace 输出 Trace 等级日志
func (log *ConsoleLogger) Trace(msg string, a ...interface{}) {
	log.log(TRACE, msg, a)
}

// Info 输出 Info 等级日志
func (log *ConsoleLogger) Info(msg string, a ...interface{}) {
	log.log(INFO, msg, a...)
}

// Warnning 输出 Warnning 等级日志
func (log *ConsoleLogger) Warnning(msg string, a ...interface{}) {
	log.log(WARNING, msg, a...)
}

// Error 输出 Error 等级日志
func (log *ConsoleLogger) Error(msg string, a ...interface{}) {
	log.log(ERROR, msg, a...)
}

// Fatal 输出 Fatal 等级日志
func (log *ConsoleLogger) Fatal(msg string, a ...interface{}) {
	log.log(FATAL, msg, a...)
}

func (log *ConsoleLogger) log(level LogLevel, msg string, a ...interface{}) {
	if level >= log.Logger.Level {
		msg = fmt.Sprintf(msg, a...)
		filename, funcName, lineNo := GetfuncRunTimeInfo(3)
		fmt.Printf(LogModel,
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
