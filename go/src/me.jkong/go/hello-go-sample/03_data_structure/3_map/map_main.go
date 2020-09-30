package main

import (
	"fmt"
	"sort"
)

func main() {

	fmt.Println("--> Map creation ... ")
	// 定义map 并 初始化数据
	mapTmp := map[string]string{
		"name": "zhang",
		"age":  "20",
	}
	fmt.Println(mapTmp)

	var map0 map[string]int
	//map0["asd"] = 123		// map 没有初始化，是编译不通过的，不允许这样操作
	fmt.Println(map0, map0 == nil) // map == nil

	map1 := make(map[string]int, 100)
	fmt.Println(map1, map1 == nil) // empty map

	fmt.Println("--> Map foreach ... ")
	// map 赋值
	mapTmp["gender"] = "male"
	for k, v := range mapTmp {
		fmt.Println(k, v)
	}
	// map 只遍历key
	for keyTemp := range mapTmp {
		fmt.Println(keyTemp)
	}
	// map 只遍历value
	for _, valueTmp := range mapTmp {
		fmt.Println(valueTmp)
	}

	fmt.Println("--> Map operation ... ")
	// 判断是否是否存在
	value, ok := mapTmp["name"]
	if ok {
		fmt.Println(value)
	}
	value, ok = mapTmp["birthday"]
	// 值不存在，获取值会得到一个空支付串
	fmt.Println(value, ok)

	// 删除键值对
	fmt.Println(mapTmp)
	delete(mapTmp, "gender")
	fmt.Println(mapTmp)

	// map 是 无序的，如果想对map内的数据进行排序，需要先将key拿出来，放到一个slice中，然后对slice中的key进行排序，最后按照key输出。
	var keys []string
	for key := range mapTmp {
		keys = append(keys, key)
	}
	sort.Strings(keys)
	for _, value := range keys {
		fmt.Println(mapTmp[value])
	}

	// 输出map 元素个数
	fmt.Println(len(mapTmp))
}

/*
map 总结：
- map 使用hash表，必须可以比较相等
- 除了 slice， map， function 的内建类型都可以作为 key
- Struct 类型不包含上述字段，也可以作为key
*/
