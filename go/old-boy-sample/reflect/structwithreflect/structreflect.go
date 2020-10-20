package main

import (
	"fmt"
	"reflect"
)

// 任意值通过reflect.TypeOf()获得反射对象信息后，如果它的类型是结构体，
// 可以通过反射值对象（reflect.Type）的NumField()和Field()方法获得结构体成员的详细信息。
// reflect.Type中与获取结构体成员相关的的方法如下表所示:
// 方法															说明
// Field(i int) StructField										根据索引，返回索引对应的结构体字段的信息。
// NumField() int												返回结构体成员字段数量。
// FieldByName(name string) (StructField, bool)					根据给定字符串返回字符串对应的结构体字段的信息。	*****
// FieldByIndex(index []int) StructField						多层成员访问时，根据 []int 提供的每个结构体的字段索引，返回字段的信息。
// FieldByNameFunc(match func(string) bool) (StructField,bool)	根据传入的匹配函数匹配需要的字段。	*****
// NumMethod() int												返回该类型的方法集中方法的数目
// Method(int) Method											返回该类型方法集中的第i个方法
// MethodByName(string)(Method, bool)							根据方法名返回该类型方法集中的方法	*****

// StructField的定义如下：
// type StructField struct {
// Name是字段的名字。PkgPath是非导出字段的包路径，对导出字段该字段为""。
// 参见http://golang.org/ref/spec#Uniqueness_of_identifiers
//     Name    		string
//     PkgPath 		string
//     Type      	Type      // 字段的类型
//     Tag       	StructTag // 字段的标签
//     Offset    	uintptr   // 字段在结构体中的字节偏移量
//     Index     	[]int     // 用于Type.FieldByIndex时的索引切片
//     Anonymous 	bool      // 是否匿名字段
// }

type student struct {
	Name string `json:"name"`
	Age  int    `json:"age"`
}

func (s student) Study() string {
	msg := "好好学习，天天向上。"
	fmt.Println(msg)
	return msg
}

func (s student) Sleep() string {
	msg := "好好睡觉，快快长大。"
	fmt.Println(msg)
	return msg
}

func main() {

	stu1 := student{
		Name: "JKong",
		Age:  18,
	}
	// 通过反射获取字段信息
	printAttr(stu1)

	// 通过反射获取方法信息并调用方法
	printMethods(stu1)
}

func printMethods(x interface{}) {
	t := reflect.TypeOf(x)
	v := reflect.ValueOf(x)

	fmt.Println(t.NumMethod())
	for i := 0; i < v.NumMethod(); i++ {
		methodType := v.Method(i).Type()
		fmt.Printf("method name:%s\n", t.Method(i).Name)
		fmt.Printf("method:%s\n", methodType)
		// 通过反射调用方法传递的参数必须是 []reflect.Value 类型
		var args = []reflect.Value{}
		// 通过反射调用方法
		v.Method(i).Call(args)
	}
}

func printAttr(x interface{}) {
	r := reflect.TypeOf(x)
	fmt.Println("type name: ", r.Name(), ", type kind: ", r.Kind())

	// 通过for循环遍历结构体的所有字段信息
	for i := 0; i < r.NumField(); i++ {
		field := r.Field(i)
		fmt.Printf("name:%s index:%d type:%v json tag:%v\n", field.Name, field.Index, field.Type, field.Tag.Get("json"))
	}

	// 通过字段名获取指定结构体字段信息
	if scoreField, ok := r.FieldByName("Score"); ok {
		fmt.Printf("name:%s index:%d type:%v json tag:%v\n", scoreField.Name, scoreField.Index, scoreField.Type, scoreField.Tag.Get("json"))
	}
}
