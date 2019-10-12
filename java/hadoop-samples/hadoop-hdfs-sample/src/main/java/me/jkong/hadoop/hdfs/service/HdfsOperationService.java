package me.jkong.hadoop.hdfs.service;

import me.jkong.hadoop.hdfs.config.Hdfs;
import me.jkong.hadoop.hdfs.entity.HdfsFolder;
import org.apache.commons.io.FileExistsException;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.protocol.HdfsLocatedFileStatus;
import org.apache.hadoop.hdfs.protocol.HdfsNamedFileStatus;
import org.apache.hadoop.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description HDFS操作逻辑
 * @date 2019/10/12 11:52.
 */
public class HdfsOperationService {

    private static FileSystem fileSystem = Hdfs.getFileSystemInstance();

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     */
    public static boolean mkDir(String path) {
        try {
            fileSystem.mkdirs(new Path(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 删除文件或目录
     *
     * @param path 路径
     * @return 是否删除成功
     */
    public static boolean rmDir(String path) {
        try {
            return fileSystem.deleteOnExit(new Path(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除文件或目录
     *
     * @param path 路径
     * @param b    是否递归删除
     * @return 是否删除成功
     */
    public static boolean rmDir(String path, boolean b) {
        try {
            // 第二个参数指是否递归删除
            return fileSystem.delete(new Path(path), b);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 文件或路径重命名
     *
     * @param oldPath 旧路径
     * @param newPath 新路径
     * @return 重命名是否成功
     */
    public static boolean reName(String oldPath, String newPath) {
        try {
            fileSystem.rename(new Path(oldPath), new Path(newPath));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 上传文件到hdfs
     *
     * @param path     文件上传路径
     * @param inStream 输入流
     * @return 是否上传成功
     */
    public static boolean upload(String path, InputStream inStream) {
        Path filePath = new Path(path);
        FSDataOutputStream outStream = null;
        try {
            FileStatus fileStatus = fileSystem.getFileStatus(filePath);
            if (fileStatus != null && fileStatus.isFile()) {
                throw new FileExistsException();
            }

            outStream = fileSystem.create(filePath,
                    () -> {
                        // 进度提醒
                        System.out.print("#");
                    });
            IOUtils.copyBytes(inStream, outStream, 4096);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.closeStream(inStream);
            IOUtils.closeStream(outStream);
        }
        return true;
    }

    /**
     * 下载文件
     *
     * @param path         路径
     * @param outputStream 输出流
     * @return 是否下载成功
     */
    public static boolean download(String path, OutputStream outputStream) {

        Path filePath = new Path(path);
        InputStream in = null;
        try {
            FileStatus fileStatus = fileSystem.getFileStatus(filePath);
            if (fileStatus == null || !fileStatus.isFile()) {
                throw new FileNotFoundException();
            }

            in = fileSystem.open(filePath);
            IOUtils.copyBytes(in, outputStream, 4096, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.closeStream(in);
        }
        return true;
    }


    /**
     * 获取目录列表
     *
     * @param path 路径
     */
    public static HdfsFolder listFileFolders(String path) {

        Path filePath = new Path(path);
        try {
            FileStatus fileStatus = fileSystem.getFileStatus(filePath);
            if (fileStatus == null) {
                throw new FileNotFoundException();
            }
            return listFileFolders(fileStatus);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HdfsFolder listFileFolders(FileStatus fileStatus) {
        Path filePath = fileStatus.getPath();
        try {
            // 如果路径是文件
            if (fileStatus.isFile()) {
                return HdfsFolder.getFileInstance((HdfsNamedFileStatus) fileStatus);
            }

            // 如果是目录
            HdfsFolder rootFolder = HdfsFolder.getFolderInstance((HdfsLocatedFileStatus) fileStatus);
            FileStatus[] fileStatuses = fileSystem.listStatus(filePath);
            List<HdfsFolder> children = new ArrayList<>();
            for (FileStatus fileStatusTmp : fileStatuses) {
                children.add(listFileFolders(fileStatusTmp));
            }
            return rootFolder.setChildren(children);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}