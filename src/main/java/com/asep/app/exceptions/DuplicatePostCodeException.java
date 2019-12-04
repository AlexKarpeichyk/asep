package com.asep.app.exceptions;

import com.asep.app.annotations.ExceptionInformation;


@ExceptionInformation(description = "EmailNotFound Custom Exception")
public class DuplicatePostCodeException extends Exception {

    public DuplicatePostCodeException(String message) {
        super(message);
    }
}