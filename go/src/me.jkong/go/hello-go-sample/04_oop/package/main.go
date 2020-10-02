// @Description: go 的包
// @Author: JKong
// @Update: 2020/10/2 3:31 下午
package main

import (
	"me.jkong/go/hello-go-sample/04_oop/package/tree"
)

// 1. 在go语言中，一般使用CamelCase类型定义
// 2. 首字母大写：public
// 3. 首字母小写：private

/*
关于 package：
- 每个目录一个包
- main包包含可执行入口
- 为结构体定义的方法必须放到同一个包内，但是可以不在同一个文件内
*/
func main() {
	var rootNode tree.Node
	rootNode = tree.Node{Value: 0}
	rootNode.PreNode = &tree.Node{Value: -1}
	rootNode.NextNode = &tree.Node{1, nil, nil}
	rootNode.PreNode.NextNode = new(tree.Node)
	rootNode.ToString()
}
