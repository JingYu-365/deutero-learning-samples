package me.jkong.object.storage.core.exception;

/**
 * 异常基类
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/22 14:35.
 */
public abstract class BaseException extends RuntimeException {
    protected String errorMessage;

    public BaseException(String message, Throwable cause) {
        super(cause);
        this.errorMessage = message;
    }

    public abstract int errorCode();

    public String getErrorMessage() {
        return errorMessage;
    }
}
