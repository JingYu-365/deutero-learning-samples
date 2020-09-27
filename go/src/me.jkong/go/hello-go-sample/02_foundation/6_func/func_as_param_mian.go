package main

import (
	"fmt"
	"reflect"
	"runtime"
	"strings"
)

func main() {

	// 函数做参数
	r1 := funcOp(123, 12, add)
	fmt.Println(r1)

	// 匿名函数
	fmt.Println(funcOp(12, 3,
		func(c, d int) int {
			return c / d
		}))

	// 函数做返回值
	op := makeSuffixFunc(".jpg")

	r2 := op("asd")
	fmt.Println(r2)

	r3 := op("123.jpg")
	fmt.Println(r3)
}

func funcOp(a, b int, op func(c, d int) int) int {
	fmt.Printf("calling function %s with args (%d, %d)\n",
		runtime.FuncForPC(reflect.ValueOf(op).Pointer()).Name(), a, b)
	return op(a, b)
}

func add(a, b int) int {
	return a + b
}

func makeSuffixFunc(suffix string) func(str string) string {
	return func(str string) string {
		if !strings.HasSuffix(str, suffix) {
			return str + suffix
		}
		return str
	}
}
