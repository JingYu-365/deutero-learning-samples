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
5. 日志文件哟有切割策略
*/
func main() {
	log := konglog.NewLogger("info")
	for {
		log.Debug("this is Debug info")
		log.Trace("this is Trace info")
		log.Info("this is Info info")
		log.Warnning("this is Warnning info")
		log.Error("this is Error info")
		log.Fatal("this is Fatal info")
		time.Sleep(time.Second * 2)
	}
}
