package tail

import (
	"fmt"

	"github.com/hpcloud/tail"
)

var tailObj *tail.Tail

// Init 初始化tail对象
func Init(fileName string) (err error) {
	config := tail.Config{
		ReOpen:    true,
		Follow:    true,
		Location:  &tail.SeekInfo{Offset: 0, Whence: 2},
		MustExist: false,
		Poll:      true,
	}

	tailObj, err = tail.TailFile(fileName, config)
	if err != nil {
		fmt.Println("tail file failed ,err :", err)
		return
	}
	return
}

// ReadChan 读取文件通道
func ReadChan() <-chan *tail.Line {
	return tailObj.Lines
}
