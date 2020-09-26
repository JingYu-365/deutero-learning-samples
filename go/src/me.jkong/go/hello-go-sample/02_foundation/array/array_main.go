package main

import (
	"fmt"
)

func main() {
	// 长度与类型一旦确定不可再修改
	var arr1 [3]string
	var arr2 [4]int

	fmt.Println(arr1, arr2)

	arr1 = [3]string{"java", "go", "python"}
	arr2 = [4]int{1, 2, 3, 4}
	fmt.Println(arr1, arr2)

	var arr3 = [3]bool{true, false, false}
	fmt.Println(arr3)

	arr4 := [3]byte{1, 2, 3}
	fmt.Println(arr4)

	// ... 表示可变长度
	arr5 := [...]string{"1", "2", "12312321"}
	fmt.Println(arr5)

	// 根绝下标赋值
	arr5[1] = "321"
	fmt.Println(arr5)

	// 遍历数组
	for i := 0; i < len(arr5); i++ {
		fmt.Println(arr5[i])
	}

	for _, v := range arr5 {
		fmt.Println(v)
	}
}
