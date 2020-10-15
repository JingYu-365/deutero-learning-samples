// @Description: TODO
// @Author: JKong
// @Update: 2020/10/2 2:37 下午
package main

import (
	"fmt"
)

type TreeNode struct {
	value             int
	preNode, nextNode *TreeNode
}

// 为结构体定义方法
func (node TreeNode) print() {
	fmt.Println(node.value)
}
func (node *TreeNode) setValue(value int) {
	node.value = value
}
func (node *TreeNode) toString() {
	if node == nil {
		return
	}

	node.preNode.toString()
	node.print()
	node.nextNode.toString()
}

func createNode(value int) *TreeNode {
	// 看是返回了局部变量，实际上方法结束时不会消掉此对象的。
	return &TreeNode{value: value}
}

func main() {
	var rootNode TreeNode
	rootNode = TreeNode{value: 0}
	rootNode.preNode = &TreeNode{value: -1}
	rootNode.nextNode = &TreeNode{1, nil, nil}
	rootNode.preNode.nextNode = new(TreeNode)

	fmt.Println(rootNode)
	rootNode.preNode.nextNode.setValue(5)
	rootNode.preNode.nextNode.print()

	nodes := []TreeNode{
		{value: 3},
		*createNode(4),
		{5, nil, &rootNode},
	}

	fmt.Println(nodes)

	rootNode.toString()
}
