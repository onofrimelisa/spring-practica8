package com.bootcamp.spring.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUrlException extends LinkTrackerException{
    public InvalidUrlException(String message) {
        super("The url " + message + " is not valid");
        setStatus(HttpStatus.BAD_REQUEST);
        setName("Invalid url");
    }
}
