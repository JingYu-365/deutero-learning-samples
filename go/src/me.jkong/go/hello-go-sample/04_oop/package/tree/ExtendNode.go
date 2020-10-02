// @Description: 扩展结构体方法
// 1. 从写方法
// @Author: JKong
// @Update: 2020/10/2 3:47 下午
package tree

// 如何扩充系统类型或者别人的类型
// - 定义别名
// - 使用组合

// 重写 node 的toString方法

type MyNode struct {
	node *Node
}

func (myNode *MyNode) ToString() {
	if myNode == nil || myNode.node == nil {
		return
	}

	pre := MyNode{myNode.node.PreNode}
	pre.ToString()
	next := MyNode{myNode.node.NextNode}
	next.ToString()

	self := MyNode{myNode.node}
	self.ToString()
}
