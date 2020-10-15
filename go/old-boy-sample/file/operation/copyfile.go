package operation

import (
	"fmt"
	"io"
	"os"
)

// CopyFile 拷贝文件
// 如果我们要复制大文件也可以用 io.copy 这个，防止产生内存溢出。
func CopyFile(srcName string, dstName string) (written int64, err error) {
	src, err := os.Open(srcName)
	if err != nil {
		fmt.Printf("open %s failed, err:%v.\n", srcName, err)
		return
	}
	defer src.Close()

	dst, err := os.OpenFile(dstName, os.O_CREATE|os.O_WRONLY, 0666)
	if err != nil {
		fmt.Printf("open %s failed, err:%v.\n", dstName, err)
		return
	}
	defer dst.Close()
	n, err := io.Copy(dst, src)
	return n, err
}
