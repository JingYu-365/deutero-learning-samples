package main

import (
	"fmt"
	"math/rand"
	"sort"
)

func main() {

	var map0 map[string]int
	//map0["asd"] = 123		// map 没有初始化，是编译不通过的，不允许这样操作
	fmt.Println(map0, map0 == nil)

	map1 := make(map[string]int, 100)
	fmt.Println(map1, map1 == nil)

	// map 赋值
	for i := 0; i < 100; i++ {
		key := fmt.Sprintf("stu%02d", i)
		value := rand.Intn(100)
		map1[key] = value
	}
	fmt.Println(map1)

	// 将map中的key存入切片keys
	keys := make([]string, 0, 100)
	for key := range map1 {
		keys = append(keys, key)
	}
	fmt.Println(keys)
	sort.Strings(keys)
	fmt.Println(keys)

	// 输出数据
	i := 0
	for _, key := range keys {
		fmt.Println(key, map1[key])
		i++
		if i == 10 {
			break
		}
	}

	// 判断键值对是否存在
	map2 := make(map[string]int, 100)
	map2["asd"] = 123
	map2["dsa"] = 321

	// 判断是否是否存在
	value, ok := map2["asd"]
	if ok {
		fmt.Println(value)
	}

	value, ok = map2["qwe"]
	fmt.Println(value, ok)

	// map 只遍历key
	for keyTemp := range map2 {
		fmt.Println(keyTemp)
	}
	// map 只遍历value
	for _, valuetemp := range map2 {
		fmt.Println(valuetemp)
	}

	// 删除键值对
	delete(map2, "asd")
	fmt.Println(map2)
}
