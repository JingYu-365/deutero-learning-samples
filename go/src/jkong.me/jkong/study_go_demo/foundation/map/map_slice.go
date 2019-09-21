package main

import "fmt"

func main() {
	var sliceMap = make(map[string][]string, 3)	// 只对map初始化，map内部数据没有初始化，使用前需要先初始化。
	fmt.Println(sliceMap)
	fmt.Println("after init")
	key := "中国"
	value, ok := sliceMap[key]
	if !ok {
		value = make([]string, 0, 2)
	}
	value = append(value, "北京", "上海")
	sliceMap[key] = value
	fmt.Println(sliceMap)
}
