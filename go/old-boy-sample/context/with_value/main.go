package main

import (
	"context"
	"fmt"
	"sync"
	"time"
)

/*
WithValue函数能够将请求作用域的数据与 Context 对象建立关系。声明如下：

func WithValue(parent Context, key, val interface{}) Context
WithValue返回父节点的副本，其中与key关联的值为val。

仅对API和进程间传递请求域的数据使用上下文值，而不是使用它来传递可选参数给函数。

所提供的键必须是可比较的，并且不应该是string类型或任何其他内置类型，
以避免使用上下文在包之间发生冲突。WithValue的用户应该为键定义自己的类型。
为了避免在分配给interface{}时进行分配，上下文键通常具有具体类型struct{}。
或者，导出的上下文关键变量的静态类型应该是指针或接口。

*/

// TraceCode 定义新的类型
type TraceCode string

var wg sync.WaitGroup

func worker(ctx context.Context) {
	key := TraceCode("TRACE_CODE")
	// 在子goroutine中获取trace code，并转为 string类型
	traceCode, ok := ctx.Value(key).(string)
	if !ok {
		fmt.Println("invalid trace code")
	}
LOOP:
	for {
		fmt.Printf("worker, trace code:%s\n", traceCode)
		// 假设正常连接数据库耗时10毫秒
		time.Sleep(time.Millisecond * 10)
		select {
		case <-ctx.Done(): // 50毫秒后自动调用
			break LOOP
		default:
		}
	}
	fmt.Println("worker done!")
	wg.Done()
}

func main() {
	// 设置一个50毫秒的超时
	ctx, cancel := context.WithTimeout(context.Background(), time.Millisecond*50)
	// 在系统的入口中设置trace code传递给后续启动的goroutine实现日志数据聚合
	ctx = context.WithValue(ctx, TraceCode("TRACE_CODE"), "12512312234")
	wg.Add(1)
	go worker(ctx)
	time.Sleep(time.Second * 5)
	cancel() // 通知子goroutine结束
	wg.Wait()
	fmt.Println("over")
}
