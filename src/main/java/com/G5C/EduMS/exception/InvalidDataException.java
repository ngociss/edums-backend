package com.G5C.EduMS.exception;

import org.springframework.http.HttpStatus;

public class InvalidDataException extends BaseException {

    public InvalidDataException(String message) {
        super(HttpStatus.BAD_REQUEST, "INVALID_DATA", message);
    }

    public InvalidDataException(String code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}
