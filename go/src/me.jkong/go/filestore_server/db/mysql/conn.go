package mydb

import (
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"os"
)

var db *sql.DB

func init() {
	db, _ = sql.Open("mysql",
		"root:root@tcp(127.0.0.1:3306)/fileserver?charset=utf8")
	db.SetMaxOpenConns(1000)
	err := db.Ping()
	if err != nil {
		fmt.Printf("failed to connect mysql, error : %s", err.Error())
		os.Exit(1)
	}
}

// 获取数据库的链接
func DBConn() *sql.DB {
	return db
}
