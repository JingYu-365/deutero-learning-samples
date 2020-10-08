package main

import (
	"time"

	"jkong.me/old-boy-sample/logger/konglog"
)

/*
日志需求：
1. 支持将日志输出到不同位置
2. 日志分级：
	- Debug
	- Trace
	- Info
	- Warn
	- Error
	- Fatal
3. 日志要支持开关控制
4. 支持定义日志格式，例如：时间 行号 文件名 日志级别 日志信息
5. 日志文件要有切割策略
	- 按照文件大小切割
	- 按照时间切割
*/
func main() {
	// log := konglog.NewLogger("console", "info")
	log := konglog.NewLogger("file", "info")
	for i := 0; i < 500; i++ {
		log.Debug("this is Debug info")
		log.Trace("this is Trace info")
		log.Info("this is Info info, with info: %s %d", "hahahha", 189)
		log.Warnning("this is Warnning info")
		log.Error("this is Error info")
		log.Fatal("this is Fatal info")
		time.Sleep(time.Millisecond * 200)
	}
}
