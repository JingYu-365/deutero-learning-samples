package main

import "fmt"

func main() {
	arr := [10]int{2,3,4,2,4,5,3,2,45,4}

	sum := 0
	for _,v :=range arr{
		sum += v
	}
	fmt.Println(sum)
}
