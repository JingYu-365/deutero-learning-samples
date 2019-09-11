package main

import "fmt"

func main() {

	num := 0
	delName := ""
	updateName := ""
	for {
		printmenu()
		fmt.Scan(&num)

		switch num {
		case 1:
			addBook()
		case 2:
			updateBook(updateName)
		case 3:
			listBooks()
		case 4:
			fmt.Scan(&delName)
			deleteBoook(delName)
		}
	}

}

func addBook() {

}

func updateBook(name string) {

}

func listBooks() {

}

func deleteBoook(i string) {

}

func printmenu()  {
	fmt.Println("菜单：")
	fmt.Println("1.添加书籍")
	fmt.Println("2.更新书籍")
	fmt.Println("3.查看书籍")
	fmt.Println("4.删除书籍")
	fmt.Println("请选择：")
}


type Book struct {
	name string
	author string
	price float32
	publish bool
}