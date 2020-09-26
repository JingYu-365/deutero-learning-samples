package main

import (
	"fmt"
	"math"
)

// go 支持数据类型：
// (u)int (u)int8 (u)int16 (u)int32 (u)int64 uintptr
// byte(8位) rune(32位)
// float32 float64 单精度 | 双精度
// complex32 complex64 复数

// 强制类型转换
func main() {
	var a, b = 3, 4
	var c = int(math.Sqrt(float64(a*a + b*b)))
	fmt.Println(c)
}

/*
变量总结：
1. 变量类型写在变量名之后
2. 编译器可以推测出变量的类型
3. 没有char类型，只有rune
4. 原生支持复数类型
*/
