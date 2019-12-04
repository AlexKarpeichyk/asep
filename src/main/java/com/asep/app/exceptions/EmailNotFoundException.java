package com.asep.app.exceptions;

import com.asep.app.annotations.ExceptionInformation;

@ExceptionInformation(description = "EmailNotFound Custom Exception")
public class EmailNotFoundException extends Exception {

    public EmailNotFoundException(String message) {
        super(message);
    }
}
