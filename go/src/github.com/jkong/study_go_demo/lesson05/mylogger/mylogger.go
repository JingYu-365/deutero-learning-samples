package mylogger

// Level 是一个自定的类型 代表日志级别
type Level uint16

// 定义日志级别
const (
	DebugLevel Level = iota
	InfoLevel
	WarningLevel
	ErrorLevel
	FatalLevel
)
