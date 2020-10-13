package main

import (
	"database/sql"
	"fmt"
	"time"

	_ "github.com/go-sql-driver/mysql"
)

const dataSourceName = "root:123456@tcp(127.0.0.1:3306)/study?charset=utf8mb4&parseTime=True"

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
	tx, err := db.Begin() // 开启事务
	if err != nil {
		if tx != nil {
			tx.Rollback() // 回滚
		}
		fmt.Printf("begin trans failed, err:%v\n", err)
		return
	}
	sqlStr1 := "Update user set password=23456 where id=?"
	ret1, err := tx.Exec(sqlStr1, 2)
	if err != nil {
		tx.Rollback() // 回滚
		fmt.Printf("exec sql1 failed, err:%v\n", err)
		return
	}
	affRow1, err := ret1.RowsAffected()
	if err != nil {
		tx.Rollback() // 回滚
		fmt.Printf("exec ret1.RowsAffected() failed, err:%v\n", err)
		return
	}

	sqlStr2 := "Update user set password=789456 where id=?"
	ret2, err := tx.Exec(sqlStr2, 2)
	if err != nil {
		tx.Rollback() // 回滚
		fmt.Printf("exec sql2 failed, err:%v\n", err)
		return
	}
	affRow2, err := ret2.RowsAffected()
	if err != nil {
		tx.Rollback() // 回滚
		fmt.Printf("exec ret1.RowsAffected() failed, err:%v\n", err)
		return
	}

	fmt.Println(affRow1, affRow2)
	if affRow1 == 1 && affRow2 == 1 {
		fmt.Println("事务提交啦...")
		tx.Commit() // 提交事务
	} else {
		tx.Rollback()
		fmt.Println("事务回滚啦...")
	}

	fmt.Println("exec trans success!")
}
