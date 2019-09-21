package logger

import (
	"fmt"
	"os"
	"path"
	"time"
)

// 向日志文件中写入日志数据

// FileLogger 文件日志结构体
type FileLogger struct {
	level    Level
	fileName string
	filePath string
	file     *os.File
	errFile  *os.File
	maxSize  int64
}

// FileLogger 文件日志结构体的构造函数
func NewFileLogger(fileName, filePath string, level Level) *FileLogger {

	fl := &FileLogger{
		fileName: fileName,
		filePath: filePath,
		level:    level,
		maxSize:  10240,
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

func (f *FileLogger) IsDebugEnabled() bool {
	return f.isEnabled(DebugLevel)
}

func (f *FileLogger) IsInfoEnabled() bool {
	return f.isEnabled(InfoLevel)
}

func (f *FileLogger) IsWarnEnabled() bool {
	return f.isEnabled(WarningLevel)
}

func (f *FileLogger) IsErrorEnabled() bool {
	return f.isEnabled(ErrorLevel)
}

func (f *FileLogger) IsFatalEnabled() bool {
	return f.isEnabled(FatalLevel)
}

func (f *FileLogger) isEnabled(level Level) bool {
	return f.level <= level
}

// Debug 方法
func (f *FileLogger) Debug(format string, args ...interface{}) {
	f.log(DebugLevel, format, args...)
}

// Info 方法
func (f *FileLogger) Info(format string, args ...interface{}) {
	f.log(InfoLevel, format, args...)
}

// Warn 方法
func (f *FileLogger) Warn(format string, args ...interface{}) {
	f.log(WarningLevel, format, args...)
}

// Error 方法
func (f *FileLogger) Error(format string, args ...interface{}) {
	f.log(ErrorLevel, format, args...)
}

// Fatal 方法
func (f *FileLogger) Fatal(format string, args ...interface{}) {
	f.log(FatalLevel, format, args...)
}

func (f *FileLogger) log(level Level, format string, args ...interface{}) {
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

	// 判断并切分文件
	if f.checkSplit(f.file) {
		f.file = f.splitFile(f.file)
	}

	// 3. 利用fmt包将msg字符串写到f.file文件中
	if _, err := fmt.Fprintf(f.file, logMsg); err != nil {
		panic(fmt.Errorf("save log msg error: %v", err))
	}

	// 如果是 error 和 fatal 级别的日志，同时写到 错误日志文件中去。
	if level >= ErrorLevel {
		if f.checkSplit(f.file) {
			f.errFile = f.splitFile(f.errFile)
		}
		if _, err := fmt.Fprintf(f.errFile, logMsg); err != nil {
			panic(fmt.Errorf("save log msg error: %v", err))
		}
	}
}

// 检查是否需要切分文件
func (f *FileLogger) checkSplit(file *os.File) bool {
	fileInfo, err := file.Stat()
	if err != nil {
		panic(err)
	}
	fileSize := fileInfo.Size()
	return fileSize > f.maxSize
}

// 切分文件
func (f *FileLogger) splitFile(file *os.File) *os.File {
	// 获取待切分文件名称
	fileName := file.Name()
	// 1 把源文件关了
	file.Close()

	// 2 备份源文件
	backFileName := fmt.Sprintf("%s_%v.back", fileName, time.Now().Unix())
	os.Rename(fileName, backFileName)

	// 3 新建一个文件
	fileObj, err := os.OpenFile(fileName, os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0664)
	if err != nil {
		panic(fmt.Errorf("open file error, file name is %s, %v", fileName, err))
	}
	return fileObj
}

func (f *FileLogger) Close() {
	f.file.Close()
	f.errFile.Close()
}
