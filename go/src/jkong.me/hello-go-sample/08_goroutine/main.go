// @Description: hello goroutine
// @Author: JKong
// @Update: 2020/10/4 3:18 下午
package main

import (
	"fmt"
	"runtime"
	"time"
)

func main() {
	//goroutineRunning()

	goroutineFailed()
}

// 非抢占式多任务处理，由协程主动交出控制权
// 那么如果协程不能主动交出控制权，其他协程将不能被执行
func goroutineFailed() {
	var a [50]int
	for i := 1; i < 50; i++ {
		go func(i int) {
			for {
				a[i]++
				// 主动释放控制权
				runtime.Gosched()
			}
		}(i)
	}
	time.Sleep(time.Millisecond)
	fmt.Println(a)
}

func goroutineRunning() {
	for i := 1; i < 100; i++ {
		// 定义匿名函数来编写具体业务逻辑
		// 也可以不使用匿名函数，定义一个函数亦可，go helloGoroutine()
		go func(i int) {
			fmt.Printf("goroutine running, no： %d \n", i)
		}(i)
	}

	time.Sleep(time.Millisecond)
}

/*
goroutine 总结：
- goroutine是轻量级线程；
- 非抢占式多任务处理，由协程主动交出控制权
- 编译器 | 解释器 | 虚拟机 方面多任务处理
- 多个协程可以在一个或多个线程上运行


goroutine 切换点：
- I/O | select
- channel
- 等待锁
函数调用
- runtime.Gosched()

(以上切换点只是参考，不能保证一定会切换，也不保证其他地方就不切换)
*/
