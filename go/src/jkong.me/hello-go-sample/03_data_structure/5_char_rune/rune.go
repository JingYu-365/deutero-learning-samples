// @Description: rune 类型，代表一个 UTF-8 字符，当需要处理中文、日文或者其他复合字符时，则需要用到 rune 类型。
//					rune 类型等价于 int32 类型。
// @Author: JKong
// @Update: 2020/10/1 7:28 上午
package main

import (
	"fmt"
	"unicode/utf8"
)

func main() {
	s := "2020.10.1 国庆中秋双节快了！"

	fmt.Println(s)

	fmt.Println(" string length ...")
	fmt.Println(len(s))
	// 包含中文或者日文时需要使用Rune方式解析字符长度
	fmt.Println(utf8.RuneCountInString(s))

	fmt.Println("foreach ch in string ...")
	bytes := []byte(s)
	for len(bytes) > 0 {
		ch, size := utf8.DecodeRune(bytes)
		bytes = bytes[size:]
		fmt.Printf("(%c %d) \n", ch, size)
	}

	for o {

	}

}
