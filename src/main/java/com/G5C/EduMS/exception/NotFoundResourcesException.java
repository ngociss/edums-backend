package com.G5C.EduMS.exception;

import com.G5C.EduMS.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NotFoundResourcesException extends BaseException {

    public NotFoundResourcesException(String message) {
        super(HttpStatus.NOT_FOUND, "NOT_FOUND", message);
    }

    public NotFoundResourcesException(String code, String message) {
        super(HttpStatus.NOT_FOUND, code, message);
    }
}
