package konglog

// Logger 日志结构体
type Logger struct {
	Level LogLevel
}

// Ilog 日志统一抽象
type Ilog interface {
	Debug(msg string, a ...interface{})
	Trace(msg string, a ...interface{})
	Info(msg string, a ...interface{})
	Warnning(msg string, a ...interface{})
	Error(msg string, a ...interface{})
	Fatal(msg string, a ...interface{})
}

// LogModel 日志格式模板
const LogModel = "[%s] [%s:%s:%d] [%s] %s \n"
