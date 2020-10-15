package main

import (
	"fmt"
	"strconv"
)

func main() {

	i := int32(2434)
	str1 := string(i)
	fmt.Println(str1)
	fmt.Println(strconv.Itoa(int(i))) // i to a 将数值转为字符

	str2 := "100000"
	// fmt.Println(int64(str2)) // 不支持将字符串通过 int64(str) 转为 数值
	ret, err := strconv.ParseInt(str2, 10, 64) // ParseInt 参数1：待转换的str；参数2：进制（二进制，八进制，十进制，十六机制）；参数3：位数
	ret2, err := strconv.Atoi(str2)            // a to i 将字符转为数值
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println(ret2)
	fmt.Println(ret)

	bool1 := "true"
	ret3, err3 := strconv.ParseBool(bool1)
	if err3 != nil {
		fmt.Println(err3)
	}
	fmt.Println(ret3)
}
