package com.bootcamp.spring.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends LinkTrackerException{
    public UnauthorizedException() {
        super("Not authorized");
        this.setStatus(HttpStatus.UNAUTHORIZED);
        setName("Not authorized");
    }
}
