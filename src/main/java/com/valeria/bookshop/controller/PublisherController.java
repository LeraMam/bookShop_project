package com.valeria.bookshop.controller;

import com.valeria.bookshop.dto.PublisherDTO;
import com.valeria.bookshop.request.CreateOrUpdatePublisherRequest;
import com.valeria.bookshop.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping()
    public List<PublisherDTO> getAllAuthors() {
        return publisherService.getAllPublishers();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public PublisherDTO addNewPublisher(@RequestBody CreateOrUpdatePublisherRequest request){
        return publisherService.addNewPublisher(request);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(path="{id}")
    public void deletePublisher(@PathVariable Long id){
        publisherService.deletePublisher(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(path= "{id}")
    public PublisherDTO updatePublisher(@PathVariable Long id, @RequestBody CreateOrUpdatePublisherRequest request){
        return publisherService.updatePublisher(id, request);
    }
}
