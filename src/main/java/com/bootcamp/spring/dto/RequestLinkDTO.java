package com.bootcamp.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class RequestLinkDTO {
    private String url;
    private String linkId;
}
