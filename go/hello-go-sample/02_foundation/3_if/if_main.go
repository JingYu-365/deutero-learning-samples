package main

import (
	"fmt"
	"io/ioutil"
)

func main() {
	age := 19

	if age >= 18 && age <= 120 {
		fmt.Println("成年人")
	} else if age < 18 && age >= 0 {
		fmt.Println("未成年人")
	} else if age < 0 || age > 120 {
		fmt.Println("神仙")
	}

	// 条件中可以赋值，赋值的变量的作用域为if语句中
	const filename = "src/me.jkong/go/hello-go-sample/02_foundation/if/asd.txt"
	if content, err := ioutil.ReadFile(filename); err == nil {
		fmt.Println(string(content))
	} else {
		fmt.Println("con not read file, with err: ", err)
	}
}
