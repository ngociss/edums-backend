package com.G5C.EduMS.exception;

import org.springframework.http.HttpStatus;

public class ExistingResourcesException extends BaseException {

    public ExistingResourcesException(String message) {
        super(HttpStatus.CONFLICT, "ALREADY_EXISTS", message);
    }

    public ExistingResourcesException(String code, String message) {
        super(HttpStatus.CONFLICT, code, message);
    }
}
