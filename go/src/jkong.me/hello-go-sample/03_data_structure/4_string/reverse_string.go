package main

import "fmt"

func main() {
	s1 := "hello中国"

	runeArr := []rune(s1)

	s2 := ""
	for i := len(runeArr) - 1; i >= 0; i-- {
		s2 = s2 + string(runeArr[i])
	}
	fmt.Println(s2)

	// 方法2：对称交换
	length := len(runeArr)
	for i := 0; i < length/2; i++ {
		runeArr[i], runeArr[length-1-i] = runeArr[length-1-i], runeArr[i]
	}

	fmt.Println(string(runeArr))
}
