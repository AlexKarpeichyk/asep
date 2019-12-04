package com.asep.app.exceptions;

import com.asep.app.annotations.ExceptionInformation;

@ExceptionInformation(description = "UserNotFound Custom Exception")
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }
}
