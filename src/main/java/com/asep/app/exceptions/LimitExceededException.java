package com.asep.app.exceptions;

import com.asep.app.annotations.ExceptionInformation;

@ExceptionInformation(description = "LimitExceededForPostcodeRequest Custom Exception")
public class LimitExceededException extends Exception {
    public LimitExceededException(String message) {
        super(message);
    }
}
