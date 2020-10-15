package main

import "fmt"

// 定义结构体
type Person struct {
	age  int
	name string
}

// 同类型属性，可以一同定义
type Person1 struct {
	name, bobby string
	age         string
}

// 匿名属性
type Person2 struct {
	string
	int
}

// 在结构中统一使用点来访问属性
func (person Person) getName() string {
	return person.name
}

// 使用对象的指针，否则传递的只是拷贝的值
func (person *Person) setName(name string) {
	person.name = name
}

type Student struct {
	Person
	score int
}

type Integer struct {
	value int
}

func main() {

	person := Person{23, "JKong"}
	fmt.Println(person)
	fmt.Println(person.name)
	fmt.Println(person.getName())
	person.setName("123123")
	fmt.Println(person.getName())
	fmt.Println(person.name)

	fmt.Println()

	student := Student{Person{24, "JKong2"}, 98}
	fmt.Println(student)
	fmt.Println(student.Person.name)
	fmt.Println(student.getName())

	num := Integer{2}
	fmt.Println(num)
}
