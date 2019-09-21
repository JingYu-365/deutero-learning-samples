package main

import "fmt"

func main() {
	for i := 0; i < 10; i++ {
		for j := 0; j < 10; j++ {
			if i == 5 && j == 6 {
				// 跳出当前层循环
				goto jkong
			}
			fmt.Println(i, j)
		}
	}
jkong:
	fmt.Println("goto there")
}
