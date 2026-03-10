package com.G5C.EduMS.exception;

import org.springframework.http.HttpStatus;

public class CannotDeleteException extends BaseException {

    public CannotDeleteException(String message) {
        super(HttpStatus.CONFLICT, "CANNOT_DELETE", message);
    }

    public CannotDeleteException(String code, String message) {
        super(HttpStatus.CONFLICT, code, message);
    }
}

