package main

import (
	"fmt"
	"reflect"
)

func main() {
	// *int类型空指针
	var a *int
	fmt.Println("var a *int IsNil:", reflect.ValueOf(a).IsNil())

	// nil值
	fmt.Println("nil IsValid:", reflect.ValueOf(nil).IsValid())

	// 实例化一个匿名结构体
	b := struct{}{}
	// 尝试从结构体中查找"abc"字段
	fmt.Println("不存在的结构体成员:", reflect.ValueOf(b).FieldByName("abc").IsValid())
	// fmt.Println("不存在的结构体成员:", reflect.ValueOf(b).FieldByName("abc").IsNil())  // panic: reflect: call of reflect.Value.IsNil on zero Value
	// 尝试从结构体中查找"abc"方法
	fmt.Println("不存在的结构体方法:", reflect.ValueOf(b).MethodByName("abc").IsValid())

	// map
	c := map[string]int{}
	// 尝试从map中查找一个不存在的键
	fmt.Println("map中不存在的键：", reflect.ValueOf(c).MapIndex(reflect.ValueOf("abc")).IsValid())
	// fmt.Println("map中不存在的键：", reflect.ValueOf(c).MapIndex(reflect.ValueOf("abc")).IsNil()) // panic: reflect: call of reflect.Value.IsNil on zero Value
}
