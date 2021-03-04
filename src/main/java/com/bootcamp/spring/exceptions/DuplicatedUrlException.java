package com.bootcamp.spring.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicatedUrlException extends LinkTrackerException{
    public DuplicatedUrlException(String message) {
        super("The url " + message + " is already present in the repository.");
        setStatus(HttpStatus.BAD_REQUEST);
        setName("Duplciated url");
    }
}
