// @Description: condition实现
// @Author: JKong
// @Update: 2020/10/22 7:34 下午
package main

import (
	"fmt"
	"sync"
	"time"
)

func main() {
	//demo01()

	demo02()

}

func demo02() {
	var lock = new(sync.Mutex)
	var cond = sync.NewCond(lock)

	flag := 0
	go func() {
		for i := 0; i <= 10; i++ {
			time.Sleep(time.Second)
			flag = i
			cond.Broadcast()
		}
	}()

	go func() {
		for flag != 5 {
			cond.L.Lock()
			cond.Wait()
			cond.L.Unlock()
		}
		fmt.Println("worker1 started to work")
		time.Sleep(3 * time.Second)
		fmt.Println("worker1 work end")
	}()

	go func() {
		for flag != 10 {
			cond.L.Lock()
			cond.Wait()
			cond.L.Unlock()
		}
		fmt.Println("worker2 started to work")
		time.Sleep(3 * time.Second)
		fmt.Println("worker2 work end")
	}()

	time.Sleep(time.Second * 15)
}

func demo01() {
	var lock = new(sync.Mutex)
	var cond = sync.NewCond(lock)

	for i := 0; i < 10; i++ {
		go func(n int) {
			cond.L.Lock()         // 获取锁
			defer cond.L.Unlock() // 最后释放锁
			cond.Wait()           // 等待

			fmt.Printf("%d complete. \n", n)
		}(i)
	}

	time.Sleep(time.Second)
	fmt.Println("signal one!")
	cond.Signal()

	time.Sleep(time.Second)
	fmt.Println("signal one!")
	cond.Signal()

	time.Sleep(time.Second)
	fmt.Println("signal left all !")
	cond.Broadcast()

	time.Sleep(time.Second)
}
