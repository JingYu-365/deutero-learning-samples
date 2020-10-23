// @Description: 使用cond模拟生产者和消费者
// @Author: JKong
// @Update: 2020/10/22 7:55 下午
package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func main() {
	go produce()

	go consume()

	time.Sleep(time.Second * 10)
}

var rwLock = new(sync.RWMutex)
var proCond = sync.NewCond(rwLock)
var conCond = sync.NewCond(rwLock.RLocker())
var data = 0

func produce() {
	for {
		time.Sleep(time.Millisecond * time.Duration(rand.Intn(80)))
		rwLock.Lock()
		if data == 10 {
			proCond.Wait()
		}
		data++
		rwLock.Unlock()
		conCond.Signal()
	}
}

func consume() {
	for true {
		time.Sleep(time.Millisecond * time.Duration(rand.Intn(150)))
		rwLock.RLock()
		if data == 0 {
			conCond.Wait()
		}
		data--
		rwLock.RUnlock()
		if data == 0 {
			proCond.Signal()
		}
		fmt.Println(data)
	}
}
