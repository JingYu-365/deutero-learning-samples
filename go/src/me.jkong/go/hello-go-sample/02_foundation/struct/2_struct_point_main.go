package main

import "fmt"

func main() {
	// 创建指针类型结构体
	animal := new(Animal)
	animal.name = "bird"
	animal.age = 5
	animal.color = "red"
	fmt.Printf("animal type is %T \n", animal) // animal type is *main.Animal
	fmt.Println(animal)                        // &{bird 5 red}
	fmt.Println(animal.name)                   // bird

	// 取结构体的地址实例化
	animal2 := &Animal{}
	animal2.name = "cat"
	animal2.color = "white"
	animal2.age = 3
	fmt.Printf("animal2 type is %T \n", animal2) // animal2 type is *main.Animal
	fmt.Println(animal2)                         // &{cat 3 white}
	fmt.Println(animal2.name)                    // cat

	// 结构体内存布局：结构体占用一块连续的内存。
	fmt.Println(&animal2.name)  // 0xc00005a3c0
	fmt.Println(&animal2.age)   // 0xc00005a3d0
	fmt.Println(&animal2.color) // 0xc00005a3d8
}

type Animal struct {
	name  string
	age   int
	color string
}
