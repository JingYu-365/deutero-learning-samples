package mylogger

import (
	"fmt"
	"os"
	"path"
	"time"
)

// 向日志文件中写入日志数据

// FileLogger 文件日志结构体
type FileLogger struct {
	level Level
	fileName string
	filePath string
	file     *os.File
	errFile  *os.File
}

// FileLogger 文件日志结构体的构造函数
func NewFileLogger(fileName, filePath string) *FileLogger {

	fl := &FileLogger{
		fileName: fileName,
		filePath: filePath,
	}
	fl.initFile()
	return fl
}

// 打开日志文件赋值给结构体
func (f *FileLogger) initFile() {
	logName := path.Join(f.filePath, f.fileName)
	// 打开文件
	// O_CREATE：如果文件不存在则创建文件
	// O_WRONLY：只写的方式写文件
	// O_APPEND：以追加的方式写入文件
	fileObj, err := os.OpenFile(logName, os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0664)
	if err != nil {
		panic(fmt.Errorf("open file error, file name is %s, %v", logName, err))
	}
	f.file = fileObj

	// 打开error日志文件
	errorLogName := fmt.Sprintf("%s.err", logName)
	errorFileObj, err := os.OpenFile(errorLogName, os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0664)
	if err != nil {
		panic(fmt.Errorf("open error file error, file name is %s, %v", logName, err))
	}
	f.errFile = errorFileObj
}

// Debug 方法
func (f *FileLogger) Debug(format string, args ...interface{}) {
	// 如果当前的日志等级大于Debug则不打印
	if f.level > DebugLevel {
		return
	}

	//f.file.Write()
	// F：生成一个文件
	msg := fmt.Sprintf(format, args...) // 得到用户记录的日志

	// 日志格式：[时间][文件：行号][函数名][日志级别] 日志信息
	nowStr := time.Now().Format("2006-01-02 15:04:05.000")
	// 3: 代表函数的调用层级数
	// 第0层：runtime.Caller(skip)
	// 第1层：getCallerInfo(3)
	// 第2层：调用 Debug(format string, args ...interface{}) 的函数信息
	fileName, funcName, line := getCallerInfo(2)
	logMsg := fmt.Sprintf("[%s][%s:%d][%s][%s] %s \n", nowStr, fileName, line, funcName, "Debug", msg)

	// 利用fmt包将msg字符串写到f.file文件中
	_, err := fmt.Fprintf(f.file, logMsg)
	if err != nil {
		panic(fmt.Errorf("debug log msg error: %v", err))
	}
	// E：生成一个对象
	//fmt.Errorf()

	// S：生成一个字符串
	//fmt.Sprintf()
}

// Info 方法
func (f *FileLogger) Info(format string, args ...interface{}) {
	// 得到用户记录的日志
	msg := fmt.Sprintf(format, args...)

	// 日志格式：[时间][文件：行号][函数名][日志级别] 日志信息
	nowStr := time.Now().Format("2006-01-02 15:04:05.000")
	fileName, funcName, line := getCallerInfo(2)
	logMsg := fmt.Sprintf("[%s][%s:%d][%s][%s] %s \n", nowStr, fileName, line, funcName, "Info", msg)

	// 利用fmt包将msg字符串写到f.file文件中
	_, err := fmt.Fprintf(f.file, logMsg)
	if err != nil {
		panic(fmt.Errorf("debug log msg error: %v", err))
	}
}

// Warn 方法
func (f *FileLogger) Warn(format string, args ...interface{}) {
	// 得到用户记录的日志
	msg := fmt.Sprintf(format, args...)

	// 日志格式：[时间][文件：行号][函数名][日志级别] 日志信息
	nowStr := time.Now().Format("2006-01-02 15:04:05.000")
	fileName, funcName, line := getCallerInfo(2)
	logMsg := fmt.Sprintf("[%s][%s:%d][%s][%s] %s \n", nowStr, fileName, line, funcName, "Warn", msg)

	// 利用fmt包将msg字符串写到f.file文件中
	_, err := fmt.Fprintf(f.file, logMsg)
	if err != nil {
		panic(fmt.Errorf("debug log msg error: %v", err))
	}
}

// Error 方法
func (f *FileLogger) Error(format string, args ...interface{}) {
	// 得到用户记录的日志
	msg := fmt.Sprintf(format, args...)

	// 日志格式：[时间][文件：行号][函数名][日志级别] 日志信息
	nowStr := time.Now().Format("2006-01-02 15:04:05.000")
	fileName, funcName, line := getCallerInfo(2)
	logMsg := fmt.Sprintf("[%s][%s:%d][%s][%s] %s \n", nowStr, fileName, line, funcName, "Error", msg)

	// 利用fmt包将msg字符串写到f.file文件中
	_, err := fmt.Fprintf(f.file, logMsg)
	if err != nil {
		panic(fmt.Errorf("debug log msg error: %v", err))
	}
}

// Fatal 方法
func (f *FileLogger) Fatal(format string, args ...interface{}) {
	// 得到用户记录的日志
	msg := fmt.Sprintf(format, args...)

	// 日志格式：[时间][文件：行号][函数名][日志级别] 日志信息
	nowStr := time.Now().Format("2006-01-02 15:04:05.000")
	fileName, funcName, line := getCallerInfo(2)
	logMsg := fmt.Sprintf("[%s][%s:%d][%s][%s] %s \n", nowStr, fileName, line, funcName, "Fatal", msg)

	// 利用fmt包将msg字符串写到f.file文件中
	_, err := fmt.Fprintf(f.file, logMsg)
	if err != nil {
		panic(fmt.Errorf("debug log msg error: %v", err))
	}
}