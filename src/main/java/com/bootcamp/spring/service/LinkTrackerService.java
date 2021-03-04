package com.bootcamp.spring.service;

import com.bootcamp.spring.dto.RequestLinkDTO;
import com.bootcamp.spring.dto.ResponseLinkDTO;
import com.bootcamp.spring.exceptions.DuplicatedUrlException;
import com.bootcamp.spring.exceptions.InvalidUrlException;
import com.bootcamp.spring.exceptions.LinkIdNotFoundException;
import com.bootcamp.spring.exceptions.UnauthorizedException;
import com.bootcamp.spring.interfaces.ILinkTrackerRepository;
import com.bootcamp.spring.interfaces.ILinkTrackerService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkTrackerService implements ILinkTrackerService {
    private UrlValidator urlValidator;
    @Autowired
    private ILinkTrackerRepository linkTrackerRepository;

    public LinkTrackerService() {
        String[] schemes = {"http","https"};
        this.urlValidator = new UrlValidator(schemes);
    }

    @Override
    public Boolean validateUrl(String url) {
        return this.urlValidator.isValid(url);
    }

    @Override
    public ResponseLinkDTO createLink(RequestLinkDTO requestLinkDTO, String password) throws InvalidUrlException, DuplicatedUrlException {
        String url = requestLinkDTO.getUrl();

        if (!this.validateUrl(url)) throw new InvalidUrlException(url);

        if (linkTrackerRepository.containsUrl(url)) throw new DuplicatedUrlException(url);

        return this.linkTrackerRepository.createLink(requestLinkDTO, password);
    }

    @Override
    public ResponseLinkDTO getUrlByLink(String linkId, String password) throws LinkIdNotFoundException, UnauthorizedException {
        Optional<ResponseLinkDTO> responseLinkDTO = this.linkTrackerRepository.getUrlByLink(linkId, true);

        if (responseLinkDTO.isEmpty()) throw new LinkIdNotFoundException(linkId);

        if (!responseLinkDTO.get().getPassword().equals(password)) throw new UnauthorizedException();

        return responseLinkDTO.get();
    }

    @Override
    public ResponseLinkDTO getUrlByLink(String linkId) throws LinkIdNotFoundException, UnauthorizedException {
        Optional<ResponseLinkDTO> responseLinkDTO = this.linkTrackerRepository.getUrlByLink(linkId, true);

        if (responseLinkDTO.isEmpty()) throw new LinkIdNotFoundException(linkId);

        if (responseLinkDTO.get().getPassword() != null) throw new UnauthorizedException();

        return responseLinkDTO.get();
    }

    @Override
    public Integer getMetric(String linkId) throws LinkIdNotFoundException {
        Optional<ResponseLinkDTO> responseLinkDTO = this.linkTrackerRepository.getUrlByLink(linkId, false);

        if (responseLinkDTO.isEmpty()) throw new LinkIdNotFoundException(linkId);

        return responseLinkDTO.get().getCounter();
    }

    @Override
    public ResponseLinkDTO invalidateLink(String linkId) throws LinkIdNotFoundException {
        Optional<ResponseLinkDTO> responseLinkDTO = this.linkTrackerRepository.invalidateLink(linkId);

        if (responseLinkDTO.isEmpty()) throw new LinkIdNotFoundException(linkId);

        return responseLinkDTO.get();
    }

}
