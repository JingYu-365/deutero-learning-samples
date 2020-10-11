package main

import (
	"fmt"
	"reflect"
)

// 在Go语言中，使用reflect.TypeOf()函数可以获得任意值的类型对象（reflect.Type），程序通过类型对象可以访问任意值的类型信息。
/*
在reflect包中定义的Kind类型如下：
type Kind uint
const (
	Invalid Kind = iota
	Bool
	Int
	Int8
	Int16
	Int32
	Int64
	Uint
	Uint8
	Uint16
	Uint32
	Uint64
	Uintptr
	Float32
	Float64
	Complex64
	Complex128
	Array
	Chan
	Func
	Interface
	Map
	Ptr
	Slice
	String
	Struct
	UnsafePointer
)
*/
func main() {
	reflectTypeValue()

	fmt.Println("=================")

	reflectTypeNameAndTypeKind()
}

type myInt int32

func reflectTypeNameAndTypeKind() {
	var a *float32            // 指针
	var b myInt               // 自定义类型
	var c rune                // 类型别名
	reflectTypeNameAndKind(a) // type name: ,     type kind: ptr
	reflectTypeNameAndKind(b) // type name: myInt,        type kind: int32
	reflectTypeNameAndKind(c) // type name: int32,        type kind: int32

	type person struct {
		name string
		age  int
	}
	type book struct{ title string }
	var d = person{
		name: "JKongGo",
		age:  18,
	}
	var e = book{title: "《Go语言》"}
	reflectTypeNameAndKind(d) // type name: person,       type kind: struct
	reflectTypeNameAndKind(e) // type name: book,         type kind: struct
}

func reflectTypeNameAndKind(x interface{}) {
	v := reflect.TypeOf(x)
	fmt.Printf("type name: %v, \t type kind: %v \n", v.Name(), v.Kind())
}

func reflectTypeValue() {
	var a int32 = 128
	reflectType(a)

	var b bool = true
	reflectType(b)

	var c float64 = 123.123
	reflectType(c)
}

func reflectType(x interface{}) {
	v := reflect.TypeOf(x)
	fmt.Printf("type: %v \n", v)
}
