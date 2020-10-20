package db

import (
	"database/sql"
	"fmt"
)

// 文件上传完成，保存meta
func OnFileUploadFinished(fileName, fileLocation, fileHash string, fileSize int64) bool {
	prepare, err := mydb.DBConn().Prepare("insert into tbl_file (file_sha1,file_name,file_size,file_addr,status) " +
		" values (?,?,?,?,1)")
	if err != nil {
		fmt.Println("failed to prepare statement, err:" + err.Error())
		return false
	}
	defer prepare.Close()

	result, err := prepare.Exec(fileHash, fileName, fileSize, fileLocation)
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	rowsAffected, err := result.RowsAffected()
	if rowsAffected <= 0 {
		fmt.Printf("file with sha1: %s has been uploaded before.", fileHash)
		return false
	}
	return true
}

type TableFile struct {
	FileHash string
	FileName sql.NullString
	FileSize sql.NullInt64
	FileAddr sql.NullString
}

// 查询文件元数据
func GetFileMeta(fileHash string) (*TableFile, error) {
	stmt, err := mydb.DBConn().Prepare("select file_sha1,file_name,file_size,file_addr from tbl_file " +
		"where file_sha1=? and status=1 limit 1")
	if err != nil {
		fmt.Println(err.Error())
		return nil, err
	}
	defer stmt.Close()

	file := TableFile{}

	err = stmt.QueryRow(fileHash).Scan(&file.FileHash, &file.FileName, &file.FileSize, &file.FileAddr)
	if err != nil {
		fmt.Println(err.Error())
		return nil, err
	}
	return &file, nil
}
