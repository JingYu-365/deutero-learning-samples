package main

import (
	"fmt"
	"sync"
)

// go run -race counter.go  查看 data race
// go tool compile -race -S counter.go
// 在编译的代码中，增加了 runtime.racefuncenter、runtime.raceread、runtime.racewrite、runtime.racefuncexit 等检测 data race 的方法。
func main() {
	var count = 0
	var wg sync.WaitGroup // 使用WaitGroup等待10个goroutine完成
	wg.Add(10)
	for i := 0; i < 10; i++ {
		go func() {
			defer wg.Done()
			for j := 0; j < 100000; j++ {
				count++ // 对变量count执行10次加1
			}
		}()
	}
	wg.Wait() // 等待10个goroutine完成
	fmt.Println(count)
}
