package main

import "fmt"

func main() {
	// 切片本身是没有数据的，是对底层Array的一个view
	// 切片是一个引用类型，是一个数组的引用
	// 一般如果将数组传递到方法，那么通过切面传递

	// 定义切片
	arr := [...]int{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}

	fmt.Println("arr[2:6]:", arr[2:6])
	fmt.Println("arr[:6]:", arr[:6])
	fmt.Println("arr[2:]:", arr[2:])
	s1 := arr[:]
	fmt.Println("arr[:]:", s1)

	fmt.Println("After update slice")
	// 切片和原数组的数据都会被修改
	updateSlice(s1)
	fmt.Println(s1)
	fmt.Println(arr)

	foreachSlice(arr[:])
	fmt.Println(arr)

	// 切片还可以继续呗切片
	fmt.Println(arr[:][2:])
}

func updateSlice(slice []int) {
	slice[2] = 100
}

// 通过slice 遍历传值
func foreachSlice(arr []int) {
	arr[0] = 10
	fmt.Println(arr)
}
