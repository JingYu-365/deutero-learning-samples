package me.jkong.object.storage.core.exception;

/**
 * 错误码
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/22 14:39.
 */
public interface ErrorCode {
    int ERROR_PERMISSION_DENIED = 403;
    int ERROR_FILE_NOT_FOUND = 404;
    int ERROR_HBASE = 500;
    int ERROR_HDFS = 501;
}
