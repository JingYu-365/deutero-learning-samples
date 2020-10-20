package operation

import (
	"fmt"
	"io/ioutil"
	"os"
)

/*
	func OpenFile(name string, flag int, perm FileMode) (*File, error) {
		...
	}
	name：要打开的文件名
	flag：打开文件的模式。 模式有以下几种：
		- os.O_WRONLY	只写
		- os.O_CREATE	创建文件
		- os.O_RDONLY	只读
		- os.O_RDWR		读写
		- os.O_TRUNC	清空
		- os.O_APPEND	追加
	perm：文件权限，一个八进制数。r（读）04，w（写）02，x（执行）01。
*/

// OpenAndWriteFile 打开并写入文件
func OpenAndWriteFile(filename string) {
	file, err := os.OpenFile(filename, os.O_CREATE|os.O_RDWR, 0666)
	if err != nil {
		fmt.Println("open file failed, err:", err)
		return
	}
	defer file.Close()
	s := `test write file!
		测试中文写入

		特殊符号：！@#￥%……&*（——）
	`
	file.WriteString(s)
	file.Write([]byte("\n"))
	file.Write([]byte(s))
}

// WriteFileWithIoUtil 使用ioutil写入文件
func WriteFileWithIoUtil(filename string) {
	s := `test write file!
	测试中文写入

	特殊符号：！@#￥%……&*（——）
	`
	err := ioutil.WriteFile(filename, []byte(s), 0666)
	if err != nil {
		fmt.Println("write file failed, err:", err)
		return
	}
}
