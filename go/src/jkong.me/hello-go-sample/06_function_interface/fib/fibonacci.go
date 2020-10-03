// @Description: 通过闭包的方式实现将斐波那契数列数据输出
// @Author: JKong
// @Update: 2020/10/3 3:22 下午
package main

import (
	"bufio"
	"fmt"
	"io"
	"strings"
)

type fibonacciGen func() int

func getFibonacci() fibonacciGen {
	a, b := 0, 1
	// 斐波那契数列计算器，即定义：fibonacciGen 具体逻辑
	return func() int {
		a, b = b, a+b
		return a
	}
}

// fibonacciGen 类型是一个 func，在go中 一个 func 是可以实现方法的，此处实现 io.Reader 接口
// 此处 fibonacciGen 实现了 io.Reader 那么就可以作为 io.Reader 实现类
func (gen fibonacciGen) Read(p []byte) (n int, err error) {
	// 将 fibonacciGen 产生的结果，使用 strings.NewReader(s) 的 Read(p) ，将产生额数据读到 []byte 中
	next := gen()
	if next > 10000 {
		return 0, io.EOF
	}
	// 将 int 转为 string
	s := fmt.Sprintf("%d\n", next)
	return strings.NewReader(s).Read(p)
}

func main() {
	gen := getFibonacci()
	consoleFileContent(gen)
}

// 输出 io.Reader 内容
func consoleFileContent(reader io.Reader) {
	scanner := bufio.NewScanner(reader)
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}
}
