package com.bootcamp.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ResponseLinkDTO {
    private String url;
    private String linkId;
    private Integer counter;
    private Boolean valid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public ResponseLinkDTO(String url, String linkId, String password) {
        this.url = url;
        this.linkId = linkId;
        this.password = password;
        this.counter = 0;
        this.valid = true;
    }
}
