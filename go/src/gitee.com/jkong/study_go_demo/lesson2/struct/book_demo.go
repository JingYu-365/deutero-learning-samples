package main

import (
    "fmt"
    "os"
)

var bookStore = make(map[string]*Book, 16)

func main() {

    num := 0
    for {
        printMenu()
        fmt.Scan(&num)

        switch num {
        case 1:
            addBook()
        case 2:
            updateBook()
        case 3:
            listBooks()
        case 4:
            deleteBook()
        case 5:
            os.Exit(0)
        }
    }

}

func addBook() {
    bookTemp := new(Book)
    fmt.Printf("name:")
    fmt.Scan(&bookTemp.name)
    fmt.Printf("author:")
    fmt.Scan(&bookTemp.author)
    fmt.Printf("price:")
    fmt.Scan(&bookTemp.price)
    fmt.Printf("publish:")
    fmt.Scan(&bookTemp.publish)

    bookStore[bookTemp.name] = bookTemp
}

func updateBook() {
    bookTemp := new(Book)
    fmt.Printf("name:")
    fmt.Scan(&bookTemp.name)
    if _, ok := bookStore[bookTemp.name]; !ok {
        fmt.Printf("book named %s not exist.", bookTemp.name)
        return
    }
    fmt.Printf("author:")
    fmt.Scan(&bookTemp.author)
    fmt.Printf("price:")
    fmt.Scan(&bookTemp.price)
    fmt.Printf("publish:")
    fmt.Scan(&bookTemp.publish)

    bookStore[bookTemp.name] = bookTemp
}

func listBooks() {
    for _, v := range bookStore {
        fmt.Printf("name:%s author:%s price:%.2f publish:%t \n", v.name, v.author, v.price, v.publish)
    }
}

func deleteBook() {
    defer func() {
        // todo process error
    }()
    var name string
    fmt.Printf("name:")
    fmt.Scan(&name)
    delete(bookStore, name)
}

func printMenu() {
    fmt.Println("菜单：")
    fmt.Println("1.添加书籍")
    fmt.Println("2.更新书籍")
    fmt.Println("3.查看书籍")
    fmt.Println("4.删除书籍")
    fmt.Println("5.退出")
    fmt.Println("请选择：")
}

type Book struct {
    name    string
    author  string
    price   float32
    publish bool
}
