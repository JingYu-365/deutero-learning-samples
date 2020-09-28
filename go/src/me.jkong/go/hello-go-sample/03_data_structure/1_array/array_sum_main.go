package main

import "fmt"

func main() {
	arr := [10]int{2, 3, 4, 2, 4, 5, 3, 2, 45, 4}
	change_arr(&arr)
	foreach(arr)
}

// 数组在传递给方法时，需要指定长度，例如：[10]int 与 [20]int 是不同的数据类型，不符合此定义的数组无法传递
// 调用 func foreach(arr [10]int) 会拷贝数组
// 如果不指定长度属于切片传值。
// 数组传递给方法时，属于 值传递
func foreach(arr [10]int) {

	for _, v := range arr {
		fmt.Println(v)
	}

	sum := 0
	for i := range arr {
		sum += arr[i]
	}
	fmt.Println("sum: ", sum)
}

// 如果想改变数组的值，需要通过指针的方式将数组传递给方法。
func change_arr(arr *[10]int) {
	arr[0] = 100
}
