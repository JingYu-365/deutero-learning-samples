package main

import "fmt"

type person struct {
	name   string
	age    int
	gender bool
	hobby  []string
}

func main() {
	var jkong person
	jkong.name = "空"
	jkong.age = 18
	jkong.gender = true
	jkong.hobby = []string{"coding", "running", "reading"}
	fmt.Println(jkong)
	fmt.Printf("%T \n", jkong)

	// console:
	// {空 18 true [coding rinning reading]}
	// main.person

	// 匿名结构体
	var s struct {
		x string
		y int
	}

	s.x = "asd"
	s.y = 100
	fmt.Printf("type: %T, value:%v \n", s, s)
}
