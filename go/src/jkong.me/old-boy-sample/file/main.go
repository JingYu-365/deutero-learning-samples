package main

import "jkong.me/old-boy-sample/file/operation"

const filename = "src/jkong.me/old-boy-sample/file/data/test.txt"

func main() {
	// operation.FileOpenAndClose(filename)

	// operation.ReadFileWithSlice(filename)z

	// operation.ReadFileWithForeach(filename)

	// operation.ReadFileWithBufIO(filename)

	operation.ReadAllFileWithIoUtil(filename)
}
