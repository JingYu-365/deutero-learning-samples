// @Description: 定义异常与异常的使用
// @Author: JKong
// @Update: 2020/10/3 6:12 下午
package main

import "errors"

type KongError struct {
	code    string
	message string
}

func (k KongError) Error() string {
	return "code: " + k.code + ", msg: " + k.message
}

func createKongError() {
	err := KongError{code: "0001", message: "自定义异常"}
	panic(err)
}

func createError() {
	err := errors.New("this is error")
	panic(err)
}

func main() {
	createKongError()

	//createError()
}
