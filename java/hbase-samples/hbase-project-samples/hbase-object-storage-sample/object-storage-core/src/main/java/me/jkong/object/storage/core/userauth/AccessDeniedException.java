package me.jkong.object.storage.core.userauth;

import me.jkong.object.storage.core.exception.BaseException;
import me.jkong.object.storage.core.exception.ErrorCode;

public class AccessDeniedException extends BaseException {

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(String resPath, long userId, String accessType) {
        super(String.format("access denied:%d->%s,%s", userId, resPath, accessType), null);
    }

    public AccessDeniedException(String resPath, long userId) {
        super(String.format("access denied:%d->%s not owner", userId, resPath), null);
    }

    @Override
    public int errorCode() {
        return ErrorCode.ERROR_PERMISSION_DENIED;
    }
}
