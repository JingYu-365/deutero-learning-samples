package main

import "jkong.me/old-boy-sample/file/operation"

const readFilename = "src/jkong.me/old-boy-sample/file/data/test.txt"
const writeFilename = "src/jkong.me/old-boy-sample/file/data/write.txt"
const writeFileWithIOUtil = "src/jkong.me/old-boy-sample/file/data/write_ioutil.txt"
const copyFileName = "src/jkong.me/old-boy-sample/file/data/copy_file.txt"

func main() {
	// openandclosefile.go
	operation.FileOpenAndClose(readFilename)

	// readfile.go
	// operation.ReadFileWithSlice(readFilename)z

	// operation.ReadFileWithForeach(readFilename)

	// operation.ReadFileWithBufIO(readFilename)

	// operation.ReadAllFileWithIoUtil(readFilename)

	// writefile.go
	// operation.OpenAndWriteFile(writeFilename)

	// operation.WriteFileWithIoUtil(writeFileWithIOUtil)

	// operation.CopyFile(readFilename, copyFileName)
}
