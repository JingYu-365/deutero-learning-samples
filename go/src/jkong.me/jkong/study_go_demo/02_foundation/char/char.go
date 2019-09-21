package main

import "fmt"

func main() {
	s1 := "golang"
	c1 := 'g'
	fmt.Println(s1, c1) // golang 103

	s2 := "中国"
	c2 := '中'
	fmt.Println(s2, c2) // 中国 20013

	s3 := "hello中国"
	fmt.Println(len(s3)) // 11

	for i := 0; i < len(s3); i++ {
		fmt.Printf("%v(%c) ", s3[i], s3[i])
	}

	fmt.Println()
	for _, r := range s3 {
		fmt.Printf("%v(%c) ", r, r)
	}

	fmt.Println()
	// 类型转换
	s4 := "JKong"
	arr := []byte(s4)
	fmt.Println(arr)
	arr[2] = 'd'
	fmt.Println(arr, string(arr))
}
