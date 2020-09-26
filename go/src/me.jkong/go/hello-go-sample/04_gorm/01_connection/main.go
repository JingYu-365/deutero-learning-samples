package main

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
	_ "github.com/jinzhu/gorm/dialects/sqlite"
)

func main() {
	connectMysql()

	//connectSQLite()
}

// 连接 MySQL
// 1. go get github.com/jinzhu/gorm/dialects/mysql
// 2. import _ "github.com/jinzhu/gorm/dialects/mysql"
func connectMysql() {
	db, err := gorm.Open("mysql", "root:123@abcd@tcp(10.10.32.83:3306)/jkong?charset=utf8&parseTime=True&loc=Local")
	if err != nil {
		panic("failed to connect database")
	}

	// 关闭连接
	defer db.Close()
}

// 连接 SQLite
// 1. go get github.com/mattn/go-sqlite3
// 2. import _ "github.com/jinzhu/gorm/dialects/sqlite"

// 异常：exec: "gcc": executable file not found in %PATH%
// 解决办法：
// 1. 下载 MinGW-w64 安装（官方地址：http://mingw-w64.org/doku.php/download）
// 2. 参考blog: https://www.cnblogs.com/zsy/p/5958170.html
func connectSQLite() {
	db, err := gorm.Open("sqlite3", "D:/SQLite/test_sqlite.db")
	if err != nil {
		panic("failed to connect database")
	}
	defer db.Close()
}
