package main

import (
	"encoding/json"
	"fmt"
)

func main() {
	var stu1 = &Student{
		ID:     1,
		Gender: "男",
		Name:   "JKong"}

	fmt.Println(stu1)
	stuJson, err := json.Marshal(stu1)
	if err != nil {
		fmt.Println(err)
	}
	fmt.Printf("%#v \n", string(stuJson)) // "{\"id\":\"1\",\"name\":\"JKong\",\"gender\":\"男\"}"

	stuStr := "{\"ID\":1,\"Name\":\"JKong-1\",\"gender\":\"男\"}"
	stu2 := &Student{}
	json.Unmarshal([]byte(stuStr), stu2)
	fmt.Println(stu2)

}

type Student struct {
	// 首字母大些，表示public
	ID     int    `json:"id,string"`		// 表示序列化成字符串
	Name   string `json:"name"`
	Gender string `json:"gender"`
}
