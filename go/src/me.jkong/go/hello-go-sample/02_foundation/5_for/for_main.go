// @Description: for 循环
// @Author: JKong
// @Update: 2020/9/27 9:04 下午
package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	readFile()

	forover()
}

// 死循环
func forover() {
	for {
		fmt.Println("asd")
	}
}

func readFile() {
	filename := "src/me.jkong/go/hello-go-sample/02_foundation/5_for/asd.txt"
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}
}

/*
for 循环总结
- for & if 后面没有括号
- if 条件里也可以定义变量
- 没有 while
- switch 不需要 break，也可以直接switch多个条件
*/
