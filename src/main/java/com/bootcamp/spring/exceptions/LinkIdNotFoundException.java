package com.bootcamp.spring.exceptions;

import org.springframework.http.HttpStatus;

public class LinkIdNotFoundException extends LinkTrackerException{
    public LinkIdNotFoundException(String message) {
        super("The linkId " + message + " is not present in the repository.");
        this.setStatus(HttpStatus.NOT_FOUND);
        setName("LinkId not found");
    }
}
