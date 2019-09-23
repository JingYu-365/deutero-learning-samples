package db

import (
	"fmt"
	"jkong.me/jkong/filestore_server/db/mysql"
)

// 用户注册
func UserSignUp(username, password string) bool {
	state, err := mydb.DBConn().Prepare("insert into tbl_user (user_name,user_pwd) values (?,?)")
	if err != nil {
		panic(err.Error())
		return false
	}
	defer state.Close()

	ret, err := state.Exec(username, password)
	if err != nil {
		panic(err.Error())
		return false
	}
	if rowsAffected, err := ret.RowsAffected(); nil == err && rowsAffected > 0 {
		return true
	}
	return false
}

type User struct {
	UserName string
	UserPwd  string
	Email    string
	Phone    string
}

// 查询用户信息
func GetUserInfo(username string) (*User, error) {
	stat, err := mydb.DBConn().Prepare("select user_name,user_pwd,email,phone from tbl_user where user_name=? limit 1")
	if err != nil {
		return nil, err
	}
	defer stat.Close()
	user := User{}

	err = stat.QueryRow(username).Scan(&user.UserName, &user.UserPwd, &user.Email, &user.Phone)
	if err != nil {
		fmt.Println(err.Error())
		return nil, err
	}
	return &user, nil
}
