package operation

import (
	"fmt"
	"os"
)

// FileOpenAndClose 测试打开文件及关闭文件
func FileOpenAndClose(fileName string) {
	file, err := os.Open(fileName)
	if err != nil {
		fmt.Println("open file field, err: ", err)
		return
	}

	defer file.Close()
}
