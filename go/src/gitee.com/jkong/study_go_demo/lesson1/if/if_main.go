package main

import "fmt"

func main() {
	age := 19

	if age >= 18 && age <= 120 {
		fmt.Println("成年人")
	} else if age < 18 && age >= 0 {
		fmt.Println("未成年人")
	} else if age < 0 || age > 120 {
		fmt.Println("神仙")
	}
	
}
