package com.bootcamp.spring.controler;

import com.bootcamp.spring.dto.ErrorDTO;
import com.bootcamp.spring.dto.RequestLinkDTO;
import com.bootcamp.spring.dto.ResponseLinkDTO;
import com.bootcamp.spring.interfaces.ILinkTrackerService;
import com.bootcamp.spring.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/link")
public class LinkTrackerController {
    @Autowired
    private ILinkTrackerService linkTrackerService;

    @PostMapping("/create")
    public ResponseLinkDTO createLink(@RequestBody RequestLinkDTO requestLinkDTO, @RequestParam String password) throws InvalidUrlException, DuplicatedUrlException {
        return this.linkTrackerService.createLink(requestLinkDTO, password);
    }

    @GetMapping(value = "/redirect/{linkId}", params = {"password"})
    public ResponseEntity<Object> redirect(@PathVariable String linkId, @RequestParam("password") String password) throws LinkIdNotFoundException, URISyntaxException, UnauthorizedException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI(this.linkTrackerService.getUrlByLink(linkId, password).getUrl()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    @GetMapping("/redirect/{linkId}")
    public ResponseEntity<Object> redirect(@PathVariable String linkId) throws LinkIdNotFoundException, URISyntaxException, UnauthorizedException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI(this.linkTrackerService.getUrlByLink(linkId).getUrl()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    @GetMapping("/metric/{linkId}")
    public Integer getMetric(@PathVariable String linkId) throws LinkIdNotFoundException {
        return this.linkTrackerService.getMetric(linkId);
    }

    @PostMapping("/invalidate/{linkId}")
    public ResponseLinkDTO invalidate(@PathVariable String linkId) throws LinkIdNotFoundException {
        return this.linkTrackerService.invalidateLink(linkId);
    }

    @ExceptionHandler(LinkTrackerException.class)
    public ResponseEntity<ErrorDTO> handleInvalidUrlException(LinkTrackerException exception) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(exception.getMessage());
        errorDTO.setName(exception.getName());
        return new ResponseEntity<>(errorDTO, exception.getStatus());
    }
}
