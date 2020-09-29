// @Description: slice 属性
// @Author: JKong
// @Update: 2020/9/29 9:46 下午
package main

import "fmt"

func main() {
	fmt.Println("Creating slice ...")
	var s []int // Zero value for slice is nil
	for i := 0; i < 100; i++ {
		printSliceAttr(s)
		s = append(s, 2*i+1)
	}
	fmt.Println(s)

	s1 := []int{2, 3, 4, 5, 6}
	printSliceAttr(s1)

	// 创建一个 len & cap == 16 的slice
	s2 := make([]int, 16)
	fmt.Println(s2)
	printSliceAttr(s2)

	// 创建一个 len == 10， cap == 16 的slice
	s3 := make([]int, 10, 16)
	fmt.Println(s3)
	printSliceAttr(s3)

	fmt.Println("Copying slice ... ")
	copy(s2, s1)
	fmt.Println(s2)

	fmt.Println("Deleting element from slice ... ")
	// 移除下标为3的元素
	s2 = append(s2[:3], s2[4:]...)
	fmt.Println(s2)
}

// cap => 1 -> 2 -> 4 -> 8 -> 16 -> 32 -> 64 -> 128
func printSliceAttr(s []int) {
	fmt.Printf("len = %d, cap = %d \n", len(s), cap(s))
}
