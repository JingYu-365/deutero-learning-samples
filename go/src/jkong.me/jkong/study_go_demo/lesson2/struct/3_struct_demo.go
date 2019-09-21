package main

import "fmt"

type student struct {
    name string
    age  int
}

/**
 * 请问下面代码的执行结果是什么？
 */
func main() {
    m := make(map[string]*student)
    stus := []student{
        {name: "小王子", age: 18},
        {name: "娜扎", age: 23},
        {name: "大王八", age: 9000},
    }

    for _, stu := range stus {
        m[stu.name] = &stu
    }
    fmt.Println(m)
    for k, v := range m {
        fmt.Println(k, "=>", v.name)
    }

    fmt.Println("=============================")
    for i := 0; i < len(stus); i++ {
        m[stus[i].name] = &stus[i]
    }
    fmt.Println(m)
    for k, v := range m {
        fmt.Println(k, "=>", v.name)
    }
}
