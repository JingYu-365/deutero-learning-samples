package initpackage

import "fmt"

var name string

func init() {
	name = "空"
	fmt.Println("initpackage#init executing.")
}

// GetName 获取name值，测试init初始化方法
func GetName() string {
	fmt.Println("initpackage#GetName executing!")
	return name
}
