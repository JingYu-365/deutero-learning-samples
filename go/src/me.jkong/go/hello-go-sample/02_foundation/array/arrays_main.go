package main

import "fmt"

func main() {
	arr1 := [2][3]int{{1, 2, 3}, {3, 4, 5}}
	fmt.Println(arr1)

	arr2 := [...][3]int{{2, 3, 4}, {4, 5, 6}, {5, 6, 7}}
	fmt.Println(arr2)

	//不支持多维数组的内层使用...
	//arr3 := [3][...]int{{1},{23},{3}}
	//fmt.Println(arr3)

	// 多维数组遍历
	for _, v := range arr2 {
		fmt.Println(v)
		for _, v2 := range v {
			fmt.Println(v2)
		}
	}

	// 数组是值类型，赋值和传参会复制整个数组。

	arr3 := arr2

	arr3[1][1] = 111111
	fmt.Println(arr2) // [[2 3 4] [4 5 6] [5 6 7]]
	fmt.Println(arr3) // [[2 3 4] [4 111111 6] [5 6 7]]
}
