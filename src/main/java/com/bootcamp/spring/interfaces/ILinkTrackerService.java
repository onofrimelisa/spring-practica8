package com.bootcamp.spring.interfaces;

import com.bootcamp.spring.dto.RequestLinkDTO;
import com.bootcamp.spring.dto.ResponseLinkDTO;
import com.bootcamp.spring.exceptions.DuplicatedUrlException;
import com.bootcamp.spring.exceptions.InvalidUrlException;
import com.bootcamp.spring.exceptions.LinkIdNotFoundException;
import com.bootcamp.spring.exceptions.UnauthorizedException;

public interface ILinkTrackerService {
    Boolean validateUrl(String url);
    ResponseLinkDTO createLink(RequestLinkDTO requestLinkDTO, String password) throws InvalidUrlException, DuplicatedUrlException;
    ResponseLinkDTO getUrlByLink(String linkId, String password) throws LinkIdNotFoundException, UnauthorizedException;
    ResponseLinkDTO getUrlByLink(String linkId) throws LinkIdNotFoundException, UnauthorizedException;
    Integer getMetric(String linkId) throws LinkIdNotFoundException;
    ResponseLinkDTO invalidateLink(String linkId) throws LinkIdNotFoundException;
}
