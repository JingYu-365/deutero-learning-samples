// @Description: go 语言 指针
// @Author: JKong
// @Update: 2020/9/27 10:22 下午
package main

/*
go 语言 指针
- go语言中所有的参数传值都是 值传递
- 只能通过指针的方式实现值传递的效果：
- var a int -->  func f(pa *int){ todo }
- var cache Cache --> func f2(cache Cache)
*/
func main() {
	var a, b = 3, 4
	swap(&a, &b)
	println(a, b)
}

func swap(a, b *int) {
	*b, *a = *a, *b
}
