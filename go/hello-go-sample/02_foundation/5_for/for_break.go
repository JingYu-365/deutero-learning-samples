package main

import "fmt"

func main() {
	flag := false
	for i := 0; i < 10; i++ {
		for j := 0; j < 10; j++ {
			if i == 5 && j == 6 {
				// 跳出当前层循环
				flag = true
				break
			}
			fmt.Println(i, j)
		}
		if flag {
			break
		}
	}

	fmt.Println("++++++++++++++")

jkong:
	for i := 0; i < 10; i++ {
		for j := 0; j < 10; j++ {
			if i == 5 && j == 6 {
				// 跳出当前标签
				break jkong
			}
			fmt.Println(i, j)
		}
	}
}
