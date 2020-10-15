package main

import (
	"fmt"
	"sync"
	"sync/atomic"
	"time"
)

// 针对基本数据类型我们还可以使用原子操作来保证并发安全，
// 因为原子操作是Go语言提供的方法它在用户态就可以完成，因此性能比加锁操作更好。
// Go语言中原子操作由内置的标准库sync/atomic提供。
/*
atomic包提供的基本类型操作：
读取操作
func LoadInt32(addr *int32) (val int32)
func LoadInt64(addr *int64) (val int64)
func LoadUint32(addr *uint32) (val uint32)
func LoadUint64(addr *uint64) (val uint64)
func LoadUintptr(addr *uintptr) (val uintptr)
func LoadPointer(addr *unsafe.Pointer) (val unsafe.Pointer)

写入操作
func StoreInt32(addr *int32, val int32)
func StoreInt64(addr *int64, val int64)
func StoreUint32(addr *uint32, val uint32)
func StoreUint64(addr *uint64, val uint64)
func StoreUintptr(addr *uintptr, val uintptr)
func StorePointer(addr *unsafe.Pointer, val unsafe.Pointer)
func AddInt32(addr *int32, delta int32) (new int32)
func AddInt64(addr *int64, delta int64) (new int64)
func AddUint32(addr *uint32, delta uint32) (new uint32)
func AddUint64(addr *uint64, delta uint64) (new uint64)

修改操作
func AddUintptr(addr *uintptr, delta uintptr) (new uintptr)
func SwapInt32(addr *int32, new int32) (old int32)
func SwapInt64(addr *int64, new int64) (old int64)
func SwapUint32(addr *uint32, new uint32) (old uint32)
func SwapUint64(addr *uint64, new uint64) (old uint64)
func SwapUintptr(addr *uintptr, new uintptr) (old uintptr)

交换操作
func SwapPointer(addr *unsafe.Pointer, new unsafe.Pointer) (old unsafe.Pointer)
func CompareAndSwapInt32(addr *int32, old, new int32) (swapped bool)
func CompareAndSwapInt64(addr *int64, old, new int64) (swapped bool)
func CompareAndSwapUint32(addr *uint32, old, new uint32) (swapped bool)
func CompareAndSwapUint64(addr *uint64, old, new uint64) (swapped bool)
func CompareAndSwapUintptr(addr *uintptr, old, new uintptr) (swapped bool)

比较并交换操作
func CompareAndSwapPointer(addr *unsafe.Pointer, old, new unsafe.Pointer) (swapped bool)
*/
func main() {
	c1 := MutexCounter{} // 使用互斥锁实现并发安全
	test(&c1)
	c2 := AtomicCounter{} // 并发安全且比互斥锁效率更高
	test(&c2)
}

func test(c Counter) {
	var wg sync.WaitGroup
	start := time.Now()
	for i := 0; i < 10000; i++ {
		wg.Add(1)
		go func() {
			c.Inc()
			wg.Done()
		}()
	}
	wg.Wait()
	end := time.Now()
	fmt.Println(c.Load(), end.Sub(start))
}

// Counter 操作抽象
type Counter interface {
	Inc()
	Load() int64
}

// MutexCounter 互斥锁版
type MutexCounter struct {
	counter int64
	lock    sync.Mutex
}

// Inc 增加1
func (mc *MutexCounter) Inc() {
	mc.lock.Lock()
	defer mc.lock.Unlock()
	mc.counter++
}

// Load 加载值
func (mc *MutexCounter) Load() int64 {
	mc.lock.Lock()
	defer mc.lock.Unlock()
	return mc.counter
}

// AtomicCounter 原子操作版
type AtomicCounter struct {
	counter int64
}

// Inc 增加1
func (ac *AtomicCounter) Inc() {
	atomic.AddInt64(&ac.counter, 1)
}

// Load 加载值
func (ac *AtomicCounter) Load() int64 {
	return atomic.LoadInt64(&ac.counter)
}
