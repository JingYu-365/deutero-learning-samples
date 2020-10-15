package main

import (
	"database/sql"
	"fmt"
	"time"

	_ "github.com/go-sql-driver/mysql"
)

const dataSourceName = "root:123456@tcp(127.0.0.1:3306)/study?charset=utf8mb4&parseTime=True"

/*
普通SQL语句执行过程：
	- 客户端对SQL语句进行占位符替换得到完整的SQL语句。
	- 客户端发送完整SQL语句到MySQL服务端
	- MySQL服务端执行完整的SQL语句并将结果返回给客户端。
预处理执行过程：
	- 把SQL语句分成两部分，命令部分与数据部分。
	- 先把命令部分发送给MySQL服务端，MySQL服务端进行SQL预处理。
	- 然后把数据部分发送给MySQL服务端，MySQL服务端对SQL语句进行占位符替换。
	- MySQL服务端执行完整的SQL语句并将结果返回给客户端。
*/

var db *sql.DB

func init() {

	// 获取数据库连接，此时不会验证用户名和密码是否正确，只会验证 dataSourceName 格式是否正确
	var err error
	db, err = sql.Open("mysql", dataSourceName)
	db.SetMaxOpenConns(12)                                 // 设置最大连接数
	db.SetMaxIdleConns(6)                                  // 设置最大空闲数
	db.SetConnMaxLifetime(time.Duration(time.Minute * 10)) // 这是空闲存活时间
	if err != nil {
		fmt.Printf("data source name: %s invalid, err: %v \n", dataSourceName, err)
		return
	}

	// 验证用户名和密码是否正确，如果用户名密码出现错区则会抛出异常
	err = db.Ping()
	if err != nil {
		fmt.Printf("open %s failed, err: %v \n", dataSourceName, err)
		return
	}
	fmt.Println("connect database success!")
}

func main() {
	insertPrepare()
	queryPrepare()
}

func insertPrepare() {
	sqlStr := "insert into user(name, username, password) values (?,?,?)"
	stmt, err := db.Prepare(sqlStr)
	if err != nil {
		fmt.Printf("prepare failed, err:%v\n", err)
		return
	}
	defer stmt.Close()
	_, err = stmt.Exec("mac", "小马", "123456")
	if err != nil {
		fmt.Printf("insert failed, err:%v\n", err)
		return
	}
	_, err = stmt.Exec("bruce", "小明", "123456")
	if err != nil {
		fmt.Printf("insert failed, err:%v\n", err)
		return
	}
	fmt.Println("insert success.")
}

func queryPrepare() {
	sqlStr := "select id, name, username, password from user where id = ? "
	// sql 预编辑
	stmt, err := db.Prepare(sqlStr)
	if err != nil {
		fmt.Printf("prepare failed, err:%v\n", err)
		return
	}
	defer stmt.Close()

	rows, err := stmt.Query(1)
	if err != nil {
		fmt.Printf("query failed, err:%v\n", err)
		return
	}
	defer rows.Close()

	// 循环读取结果集中的数据
	for rows.Next() {
		var u user
		err := rows.Scan(&u.id, &u.name, &u.username, &u.password)
		if err != nil {
			fmt.Printf("scan failed, err:%v\n", err)
			return
		}
		fmt.Println(u)
	}
}

type user struct {
	id       int64
	name     string
	username string
	password string
}
