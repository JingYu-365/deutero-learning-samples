package main

import (
	"fmt"
	"strconv"
	"sync"
)

// Go 提供的map不是并发安全的，考虑到Map使用的频率很高，在sync包中提供了支持并发的sync.Map来支持Map的并发操作
// 创建 sync.Map 对象
var syncMap sync.Map

func main() {
	var wg sync.WaitGroup

	for i := 0; i < 21; i++ {
		wg.Add(1)
		go func(n int) {
			key := strconv.Itoa(n)
			// 使用 sync.Map 提供的 Store 方法将数据存储到Map中
			syncMap.Store(key, n)
			// 使用 sync.Map 提供的 Load 方法，根据key获取value值
			value, ok := syncMap.Load(key)
			if ok {
				fmt.Printf("key: %s, value: %d \n", key, value)
			}
			wg.Done()
		}(i)
	}
	wg.Wait()
}
