package com.bootcamp.spring.repository;

import com.bootcamp.spring.dto.RequestLinkDTO;
import com.bootcamp.spring.dto.ResponseLinkDTO;
import com.bootcamp.spring.interfaces.ILinkTrackerRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Repository
public class LinkTrackcerRepository implements ILinkTrackerRepository {
    private HashMap<String, ResponseLinkDTO> urlWithLinksList;

    public LinkTrackcerRepository() {
        this.urlWithLinksList = new HashMap<>();
    }

    @Override
    public ResponseLinkDTO createLink(RequestLinkDTO requestLinkDTO, String password) {
        ResponseLinkDTO responseLinkDTO = new ResponseLinkDTO(requestLinkDTO.getUrl(), requestLinkDTO.getLinkId(), password);
        this.urlWithLinksList.put(requestLinkDTO.getLinkId(), responseLinkDTO);
        return responseLinkDTO;
    }

    @Override
    public Boolean containsUrl(String url) {
        Optional<ResponseLinkDTO> linkDTO =  this.urlWithLinksList
            .entrySet()
            .stream()
            .map(Map.Entry::getValue)
            .filter(responseLinkDTO -> responseLinkDTO.getValid() && responseLinkDTO.getUrl().toUpperCase(Locale.ROOT).equals(url.toUpperCase(Locale.ROOT)))
            .findFirst();

        return linkDTO.isPresent();
    }

    @Override
    public Optional<ResponseLinkDTO> getUrlByLink(String linkId, Boolean increment) {
        Optional<ResponseLinkDTO> responseDTO = this.findLinkByLinkId(linkId);

        if(increment && responseDTO.isPresent()){
            ResponseLinkDTO link = responseDTO.get();
            link.setCounter(link.getCounter() + 1);
            this.urlWithLinksList.replace(link.getLinkId(), link);
        }

        return responseDTO;
    }

    @Override
    public Optional<ResponseLinkDTO> invalidateLink(String linkId) {
        Optional<ResponseLinkDTO> responseLinkDTO = this.getUrlByLink(linkId, false);
        responseLinkDTO.ifPresent(linkDTO -> linkDTO.setValid(false));
        return responseLinkDTO;
    }

    private Optional<ResponseLinkDTO> findLinkByLinkId(String linkId){
        return this.urlWithLinksList
            .entrySet()
            .stream()
            .map(Map.Entry::getValue)
            .filter(responseLinkDTO -> responseLinkDTO.getValid() && responseLinkDTO.getLinkId().toUpperCase(Locale.ROOT).equals(linkId.toUpperCase(Locale.ROOT)))
            .findFirst();
    }
}
