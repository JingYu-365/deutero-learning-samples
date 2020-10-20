// @Description: 使用闭包函数实现遍历二叉树
// @Author: JKong
// @Update: 2020/10/3 3:43 下午
package main

import (
	"fmt"
)

func main() {
	// -1 0 1 0
	var rootNode Node
	rootNode = Node{Value: 0}
	rootNode.PreNode = &Node{Value: -1}
	rootNode.NextNode = &Node{1, nil, nil}
	rootNode.NextNode.NextNode = new(Node)
	rootNode.ToString()

	// 统计 node 节点数
	var count int
	rootNode.extendToString(func(node *Node) {
		count++
	})
	fmt.Println("node count:", count)
}

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
	node.extendToString(func(node *Node) {
		fmt.Println(node.Value)
	})
}

func (node *Node) extendToString(f func(node *Node)) {
	if node == nil {
		return
	}
	node.PreNode.extendToString(f)
	f(node)
	node.NextNode.extendToString(f)
}
