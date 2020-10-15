// @Description: 树节点
// @Author: JKong
// @Update: 2020/10/2 2:37 下午
package tree

import (
	"fmt"
)

type Node struct {
	Value             int
	PreNode, NextNode *Node
}

// 为结构体定义方法
func (node Node) Print() {
	fmt.Println(node.Value)
}
func (node *Node) SetValue(value int) {
	node.Value = value
}
func (node *Node) ToString() {
	if node == nil {
		return
	}

	node.PreNode.ToString()
	node.Print()
	node.NextNode.ToString()
}

func CreateNode(value int) *Node {
	// 看是返回了局部变量，实际上方法结束时不会消掉此对象的。
	return &Node{Value: value}
}
