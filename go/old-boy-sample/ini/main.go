package main

import "fmt"

// MysqlConfig MySQL 数据库配置映射类
type MysqlConfig struct {
	Address  string `ini:"address"`
	Port     int    `ini:"port"`
	Username string `ini:"username"`
	Pwd      string `ini:"password"`
}

func main() {
	var mc MysqlConfig
	loadIni(&mc)
	fmt.Println(mc)
}

func loadIni(mc *MysqlConfig) {
	// todo 使用反射完成字段值映射
}
