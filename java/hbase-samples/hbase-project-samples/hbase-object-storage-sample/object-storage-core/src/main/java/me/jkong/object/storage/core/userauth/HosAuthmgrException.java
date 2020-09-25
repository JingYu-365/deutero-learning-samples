package me.jkong.object.storage.core.userauth;

import me.jkong.object.storage.core.exception.BaseException;

public class HosAuthmgrException extends BaseException {

    private int code;
    private String message;

    public HosAuthmgrException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public HosAuthmgrException(int code, String message) {
        super(message, null);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int errorCode() {
        return this.code;
    }
}