// @Description: 数据结构 queue
// @Author: JKong
// @Update: 2020/10/2 5:22 下午
package queue

import "fmt"

// 定义数据类型 queue
type Queue []int

// 设置方法

// 添加元素
func (q *Queue) Push(v int) {
	*q = append(*q, v)
}

// 移除元素
func (q *Queue) Pop() int {
	head := (*q)[0]
	*q = (*q)[1:]
	return head
}

// 是否为空
func (q *Queue) IsEmpty() bool {
	return len(*q) == 0
}

// 打印
func (q *Queue) ToString() {
	fmt.Print("[")
	for _, v := range *q {
		fmt.Print(v, ", ")
	}
	fmt.Print("]\n")
}
