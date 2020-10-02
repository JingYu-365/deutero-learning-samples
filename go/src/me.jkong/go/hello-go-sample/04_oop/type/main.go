// @Description: TODO
// @Author: JKong
// @Update: 2020/10/2 5:27 下午
package main

import (
	"fmt"
	queue "me.jkong/go/hello-go-sample/04_oop/type/queue"
)

func main() {
	testQueue()
}

func testQueue() {
	q := queue.Queue{1}
	q.Push(2)
	q.Push(3)

	q.ToString()

	q.Pop()
	q.ToString()

	q.Pop()
	fmt.Println(q.IsEmpty())

	q.Pop()
	fmt.Println(q.IsEmpty())
}
