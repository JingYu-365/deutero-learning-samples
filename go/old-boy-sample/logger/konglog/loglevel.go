package konglog

import (
	"errors"
	"strings"
)

// LogLevel 日志等级
type LogLevel uint

const (
	// DEBUG debug 等级
	DEBUG LogLevel = iota
	// TRACE trace 等级
	TRACE
	// INFO info 等级
	INFO
	// WARNING warning 等级
	WARNING
	// ERROR error 等级
	ERROR
	// FATAL fatal 等级
	FATAL
)

func parseLogLevel(s string) (LogLevel, error) {
	switch strings.ToUpper(s) {
	case "DEBUG":
		return DEBUG, nil
	case "TRACE":
		return TRACE, nil
	case "INFO":
		return INFO, nil
	case "WARNING":
		return WARNING, nil
	case "ERROR":
		return ERROR, nil
	case "FATAL":
		return FATAL, nil
	default:
		return INFO, errors.New("loglevel is changged. {log level is INFO}")
	}
}

func parseLogLevelForString(level LogLevel) string {
	var levelStr string
	switch level {
	case DEBUG:
		levelStr = "DEBUG"
	case TRACE:
		levelStr = "TRACE"
	case INFO:
		levelStr = "INFO"
	case WARNING:
		levelStr = "WARNING"
	case ERROR:
		levelStr = "ERROR"
	case FATAL:
		levelStr = "FATAL"
	}
	return levelStr
}
