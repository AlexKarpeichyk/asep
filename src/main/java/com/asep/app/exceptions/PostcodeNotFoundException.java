package com.asep.app.exceptions;

import com.asep.app.annotations.ExceptionInformation;

@ExceptionInformation(description = "PostCodeNotFound Custom Exception")
public class PostcodeNotFoundException extends Exception {

    public PostcodeNotFoundException(String message) {
        super(message);
    }
}
