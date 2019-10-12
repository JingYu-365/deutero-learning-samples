package me.jkong.hadoop.hdfs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.hadoop.hdfs.protocol.HdfsLocatedFileStatus;
import org.apache.hadoop.hdfs.protocol.HdfsNamedFileStatus;

import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description 目录结构的实体类
 * @date 2019/10/12 11:32.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HdfsFolder {

    /**
     * 目录节点的id
     */
    private long id;

    /**
     * 目录节点的名字
     */
    private String name;

    /**
     * 文件路径
     */
//    private String path;

    /**
     * 子节点数量
     */
    private int childrenNum;

    /**
     * 是否为文件夹
     */
    private FileTypeEnum type;

    /**
     * 如果未文件，大小为多少（默认为0）
     */
    private long size;
    /**
     * 修改时间
     */
    private long modificationTime;
    /**
     * 此目录下的子目录节点
     */
    private List<HdfsFolder> children;

    public static HdfsFolder getFileInstance(HdfsNamedFileStatus fileStatus) {
        return new HdfsFolder().setId(fileStatus.getFileId())
                .setName(fileStatus.getPath().getName())
                .setChildrenNum(fileStatus.getChildrenNum())
                .setSize(fileStatus.getLen())
                .setType(FileTypeEnum.FILE)
                .setModificationTime(fileStatus.getModificationTime());
    }

    public static HdfsFolder getFolderInstance(HdfsLocatedFileStatus fileStatus) {
        return new HdfsFolder().setId(fileStatus.getFileId())
                .setName(fileStatus.getPath().getName())
                .setChildrenNum(fileStatus.getChildrenNum())
                .setSize(fileStatus.getLen())
                .setType(FileTypeEnum.DIRECTORY)
                .setModificationTime(fileStatus.getModificationTime());
    }

    enum FileTypeEnum {
        /**
         * 文件
         */
        FILE,
        /**
         * 目录
         */
        DIRECTORY
    }
}