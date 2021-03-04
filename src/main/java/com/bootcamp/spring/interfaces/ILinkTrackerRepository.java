package com.bootcamp.spring.interfaces;

import com.bootcamp.spring.dto.RequestLinkDTO;
import com.bootcamp.spring.dto.ResponseLinkDTO;

import java.util.Optional;

public interface ILinkTrackerRepository {
    ResponseLinkDTO createLink(RequestLinkDTO requestLinkDTO, String password);
    Boolean containsUrl(String url);
    Optional<ResponseLinkDTO> getUrlByLink(String linkId, Boolean increment);
    Optional<ResponseLinkDTO> invalidateLink(String linkId);
}
