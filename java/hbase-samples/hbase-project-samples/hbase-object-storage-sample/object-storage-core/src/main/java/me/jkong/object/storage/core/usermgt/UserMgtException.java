package me.jkong.object.storage.core.usermgt;

import me.jkong.object.storage.core.exception.BaseException;

/**
 * 用户管理异常
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/22 15:03.
 */
public class UserMgtException extends BaseException {

    private int code;

    private String message;

    public UserMgtException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public UserMgtException(String message, int code) {
        super(message, null);
        this.code = code;
        this.message = message;
    }

    @Override
    public int errorCode() {
        return 0;
    }
}
