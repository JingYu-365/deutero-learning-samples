package main

import (
	"encoding/json"
	"fmt"
)

// json 与 结构体相互转换
func main() {
	// 将结构体转为json
	structToJSON()

	// 将json转为结构体
	jsonToStruct()
}

// Person 结构体
type Person struct {
	Name   string
	age    int
	Gender bool
}

func structToJSON() {
	p := Person{Name: "JKong", age: 18, Gender: true}
	// 在序列化时，仅对“public”的属性进行序列化
	b, err := json.Marshal(p)
	if err != nil {
		fmt.Printf("json marshal err: %s", err.Error())
	}
	fmt.Printf("%s \n", string(b))

	// console：
	// {"Name":"JKong","Gender":true}
}

func jsonToStruct() {
	// 即使json中存在非“public”属性的值，也不会被反序列化
	// todo 注意：反序列化时，key值可以非手字母大写。
	s := `{"name":"JKong","Gender":true,"age":18}`
	p := &Person{}
	err := json.Unmarshal([]byte(s), p)
	if err != nil {
		fmt.Println("json unmarshal field!")
		return
	}
	fmt.Println(p)
	// console：
	// &{JKong 0 true}

}
