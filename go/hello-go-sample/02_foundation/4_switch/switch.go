// @Description: 条件循环 switch
// @Author: JKong
// @Update: 2020/9/27 7:34 上午
package main

import "fmt"

func main() {
	println(eval(12, 3, "/"))

	println(
		grade(52),
		grade(72),
		grade(82),
		grade(92),
		//grade(121),
	)
}

// 输入两个整数，根据操作符进行不同操作
func eval(a int, b int, op string) int {
	var result int
	switch op {
	case "+":
		result = a + b
	case "-":
		result = a - b
	case "*":
		result = a * b
	case "/":
		result = a / b
	default:
		panic("unsupported operator: " + op)
	}
	return result
}

// case 后使用表达式
func grade(score int) string {
	grade := ""
	switch {
	case score < 0 || score > 100:
		panic(fmt.Sprintf("wrong score : %d", score))
	case score < 60:
		grade = "D"
	case score < 80:
		grade = "C"
	case score < 90:
		grade = "B"
	case score <= 100:
		grade = "A"
	}
	return grade
}
