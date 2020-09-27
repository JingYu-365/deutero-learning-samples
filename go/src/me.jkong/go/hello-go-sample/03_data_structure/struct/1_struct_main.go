package main

import "fmt"

func main() {
	// 实例结构体
	person := Person{name: "JKong", id: 1, age: 26}
	fmt.Println(person)

	// 通过属性赋值
	var person1 Person
	person1.id = 1
	person1.age = 23
	person1.name = "asd"
	fmt.Println(person1)
	fmt.Println(person1.name)

	// 匿名结构体
	var user struct {
		name string
		addr string
	}
	user.name = "qwe"
	user.addr = "suzhou"
	fmt.Println(user)
}

type Person struct {
	id   int
	name string
	age  int
}

type person1 struct {
	// 相同类型可以放到一行
	name, city string
	age        int8
}

//
