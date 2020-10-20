package main

import (
	"database/sql"
	"fmt"
	"time"

	// 引入但不使用，仅仅触发 init 方法，在MySQL的init方法中注册 MySQLDriver 驱动到 database/sql 中：
	// sql.Register("mysql", &MySQLDriver{})
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
	// 查询单条
	queryOne()

	fmt.Println("========= insert")
	// 插入数据
	id := insert()

	fmt.Println("========= put")
	// 更新数据
	put(id)
	queryRows()

	fmt.Println("========= delete")
	// 删除数据
	delete(id)

	// 查询多条
	queryRows()
}

func insert() int64 {
	insertSQL := "insert into user(name, username, password) values(?,?,?)"
	ret, err := db.Exec(insertSQL, "zhang", "zhang_nick", "123456")
	if err != nil {
		fmt.Printf("insert failed, err:%v\n", err)
		return 0
	}
	var theID int64
	theID, err = ret.LastInsertId() // 新插入数据的id
	if err != nil {
		fmt.Printf("get lastinsert ID failed, err:%v\n", err)
		return 0
	}
	fmt.Printf("insert success, the id is %d.\n", theID)
	return theID
}

func delete(id int64) {
	sqlStr := "delete from user where id = ?"
	ret, err := db.Exec(sqlStr, id)
	if err != nil {
		fmt.Printf("delete failed, err:%v\n", err)
		return
	}
	n, err := ret.RowsAffected() // 操作影响的行数
	if err != nil {
		fmt.Printf("get RowsAffected failed, err:%v\n", err)
		return
	}
	fmt.Printf("delete success, affected rows:%d\n", n)
}

func put(id int64) {
	sqlStr := "update user set password=? where id = ?"
	ret, err := db.Exec(sqlStr, "654321", id)
	if err != nil {
		fmt.Printf("update failed, err:%v\n", err)
		return
	}
	n, err := ret.RowsAffected() // 操作影响的行数
	if err != nil {
		fmt.Printf("get RowsAffected failed, err:%v\n", err)
		return
	}
	fmt.Printf("update success, affected rows:%d\n", n)
}

func queryOne() {
	// 从连接池中获取一个连接去数据库中查询
	row := db.QueryRow("select id, name, username, password from user where id = ? ", 1)

	var u user
	err := row.Scan(&u.id, &u.name, &u.username, &u.password)
	if err != nil {
		fmt.Printf("scan failed, err: %v \n", err)
		return
	}
	fmt.Println(u)

}

func queryRows() {
	// 从连接池中获取一个连接去数据库中查询
	rows, err := db.Query("select id, name, username, password from user where id > ?", 0)
	if err != nil {
		fmt.Printf("execute query sql error, err: %v \n", err)
	}
	defer rows.Close()

	for rows.Next() {
		var u user
		err = rows.Scan(&u.id, &u.name, &u.username, &u.password)
		if err != nil {
			fmt.Printf("scan failed, err: %v \n", err)
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
