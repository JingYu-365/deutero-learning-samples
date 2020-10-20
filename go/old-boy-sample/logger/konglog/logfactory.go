package konglog

import "strings"

// NewLogger 日志工厂
func NewLogger(loggerType string, logLevel string) Ilog {

	level, err := parseLogLevel(logLevel)
	if err != nil {
		panic(err)
	}

	switch strings.ToUpper(loggerType) {
	case CONSOLE:
		return newConsoleLogger(level)
	case FILE:
		return newFileLogger(level)
	default:
		panic("No such logger")
	}
}

const (
	// CONSOLE 控制台打印
	CONSOLE = "CONSOLE"
	// FILE 文件输出
	FILE = "FILE"
)
