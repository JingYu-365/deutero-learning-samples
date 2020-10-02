package meta

import "jkong.me/jkong/filestore_server/db"

// 文件元信息结构
type FileMeta struct {
	FileSha1 string
	FileName string
	FileSize int64
	Location string
	UploadAt string
}

var fileMetas map[string]FileMeta

//初始化
func init() {
	fileMetas = make(map[string]FileMeta)
}

// 新增或更新文件元信息
func UpdateFileMeta(fmeta FileMeta) {
	fileMetas[fmeta.FileSha1] = fmeta
}

// 根据sha1获取文件元信息
func GetFileMeta(fileSha1 string) FileMeta {
	return fileMetas[fileSha1]
}

// 获取最新的meta数据
func GetLastFileMetas(limitNum int) []FileMeta {
	//for i := 0; i <= limitNum; i++ {
	//
	//}
	return nil
}

// 根据sha1删除文件元信息
func DeleteFileMeta(fileSha1 string) {
	delete(fileMetas, fileSha1)
}

//======================= save file meta to db ========================
func UpdateFileMetaDB(fileMeta FileMeta) bool {
	return db.OnFileUploadFinished(fileMeta.FileName, fileMeta.Location, fileMeta.FileSha1, fileMeta.FileSize)
}

// 获取文件元信息
func GetFileMetaDB(fileSha1 string) (FileMeta, error) {
	file, err := db.GetFileMeta(fileSha1)
	if err != nil {
		return FileMeta{}, err
	}

	fileMeta := FileMeta{
		FileSha1: file.FileHash,
		FileName: file.FileName.String,
		FileSize: file.FileSize.Int64,
		Location: file.FileAddr.String,
	}
	return fileMeta, nil
}
