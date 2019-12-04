package com.asep.app.exceptions;

import com.asep.app.annotations.ExceptionInformation;

@ExceptionInformation(description = "LimitExceededForPostcodeRequest Custom Exception")
public class NoPropertiesFoundException extends Exception {
    public NoPropertiesFoundException(String message) {
        super(message);
    }
}