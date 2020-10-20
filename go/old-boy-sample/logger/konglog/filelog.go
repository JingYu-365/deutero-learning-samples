package konglog

import (
	"fmt"
	"os"
	"path"
	"time"
)

// FileLogger 文件输出
type FileLogger struct {
	Logger Logger

	// 其他私有属性
	filePath    string   // 文件路径
	fileName    string   // 文件名
	fileObj     *os.File // 正常文件对象
	errFileObj  *os.File // 错误日志文件对象
	maxFileSize int64    // 文件最大大小
}

func newFileLogger(logLevel LogLevel) *FileLogger {
	// 读取日志文件配置
	f := initFileLoggerObj(logLevel)
	// 初始化文件
	err := f.initFile()
	if err != nil {
		panic(err)
	}
	return f
}

func initFileLoggerObj(logLevel LogLevel) *FileLogger {

	// todo 初始化配置 filePath fileName maxFileSize（读取配置文件，若无配置采用默认值）
	filePath := "./"
	fileName := "test.log"
	maxFileSize := int64(10 * 1024)

	return &FileLogger{
		Logger:      Logger{Level: logLevel},
		filePath:    filePath,
		fileName:    fileName,
		maxFileSize: maxFileSize,
	}
}

func (log *FileLogger) initFile() error {
	fullFileName := path.Join(log.filePath, log.fileName)
	fileObj, err := os.OpenFile(fullFileName, os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0644)
	if err != nil {
		fmt.Printf("open log file failed, err: %v \n", err)
		return err
	}

	errFileObj, err := os.OpenFile(fullFileName+".err", os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0644)
	if err != nil {
		fmt.Printf("open error log file failed, err: %v \n", err)
		return err
	}
	log.fileObj = fileObj
	log.errFileObj = errFileObj
	return nil
}

// Debug 输出 Debug 等级日志
func (log *FileLogger) Debug(msg string, a ...interface{}) {
	log.log(DEBUG, msg, a)
}

// Trace 输出 Trace 等级日志
func (log *FileLogger) Trace(msg string, a ...interface{}) {
	log.log(TRACE, msg, a)
}

// Info 输出 Info 等级日志
func (log *FileLogger) Info(msg string, a ...interface{}) {
	log.log(INFO, msg, a...)
}

// Warnning 输出 Warnning 等级日志
func (log *FileLogger) Warnning(msg string, a ...interface{}) {
	log.log(WARNING, msg, a...)
}

// Error 输出 Error 等级日志
func (log *FileLogger) Error(msg string, a ...interface{}) {
	log.log(ERROR, msg, a...)
}

// Fatal 输出 Fatal 等级日志
func (log *FileLogger) Fatal(msg string, a ...interface{}) {
	log.log(FATAL, msg, a...)
}

func (log *FileLogger) log(level LogLevel, msg string, a ...interface{}) {
	if level >= log.Logger.Level {
		var f *os.File
		normalFileFlag := true
		if level >= ERROR {
			normalFileFlag = false
			f = log.errFileObj
		} else {
			f = log.fileObj
		}
		// 判断是否需要切分文件
		if GetFileSize(f) >= log.maxFileSize {
			newFile := SliceFile(f, getFileBakName(f))
			if normalFileFlag {
				log.fileObj = newFile
			} else {
				log.errFileObj = newFile
			}
			f = newFile
		}
		// 输出日志
		msg = fmt.Sprintf(msg, a...)
		filename, funcName, lineNo := GetfuncRunTimeInfo(3)
		fmt.Fprintf(f, LogModel,
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

func getFileBakName(file *os.File) string {
	filename := file.Name()
	nowStr := time.Now().Format("20060102150405")
	return filename + "." + nowStr
}

// Close 关闭文件
func (log *FileLogger) Close() {
	log.fileObj.Close()
	log.errFileObj.Close()
}
