package main

import (
	"fmt"
	"sync"
)

// 基于 once 实现单例
func main() {
	instance := GetInstance()
	fmt.Println(instance.name)
}

// Single 单例结构体
type Single struct {
	name string
}

var once sync.Once
var instance *Single

// GetInstance 获取单例对象
func GetInstance() *Single {
	once.Do(func() {
		instance = &Single{
			name: "JKong",
		}
	})
	return instance
}

/*
once#Do 源码分析：
func (o *Once) Do(f func()) {
	if atomic.LoadUint32(&o.done) == 0 {
		o.m.Lock()
		defer o.m.Unlock()
		if o.done == 0 {
			defer atomic.StoreUint32(&o.done, 1)
			f()
		}
	}
}
*/
