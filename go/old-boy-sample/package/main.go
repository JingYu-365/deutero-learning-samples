package main

import (
	"fmt"

	"jkong.me/old-boy-sample/package/calculation"
	"jkong.me/old-boy-sample/package/initpackage"
)

const pi = 3.1415926

func init() {
	fmt.Println("main#init executing.")
}

/*
	包内初始化过程：
	1. 全局声明 初始化
	2. init 方法执行
	3. main 方法执行
*/

func main() {

	/*
		console:
			initpackage#init executing.
			main#init executing.
			3
			initpackage#GetName executing!
			空
	*/

	executeCalculation()

	initPackageProcess()

}
func initPackageProcess() {
	name := initpackage.GetName()
	fmt.Println(name)
}

func executeCalculation() {
	result := calculation.Add(1, 2)
	fmt.Println(result)
}
