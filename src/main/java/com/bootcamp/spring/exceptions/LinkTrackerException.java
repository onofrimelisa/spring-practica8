package com.bootcamp.spring.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public abstract class LinkTrackerException extends Exception{
    private String name;
    private String description;
    private HttpStatus status;

    public LinkTrackerException(String message) {
        super(message);
    }
}
