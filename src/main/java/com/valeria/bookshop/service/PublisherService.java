package com.valeria.bookshop.service;

import com.valeria.bookshop.db.entity.PublisherEntity;
import com.valeria.bookshop.db.repository.PublisherRepository;
import com.valeria.bookshop.dto.PublisherDTO;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.exception.NotFoundException;
import com.valeria.bookshop.mapper.PublisherMapper;
import com.valeria.bookshop.request.CreateOrUpdatePublisherRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }

    public List<PublisherDTO> getAllPublishers() {
        return publisherMapper.mapEntitiesToDTOs(publisherRepository.findAll());
    }

    public PublisherDTO addNewPublisher(CreateOrUpdatePublisherRequest request) {
        publisherRepository.findPublisherByName(request.name()).ifPresent(PublisherEntity -> {
            throw new BadRequestException("Publisher " + request.name() + " already exists");
        });
        PublisherEntity publisher = publisherMapper.mapToEntity(request);
        return publisherMapper.mapEntityToDto(publisherRepository.save(publisher));
    }

    public PublisherEntity findEntityById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Publisher not exist"));
    }

    public void deletePublisher(Long id) {
        boolean exists = publisherRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Publisher not exist");
        }
        publisherRepository.deleteById(id);
    }

    @Transactional
    public PublisherDTO updatePublisher(Long id, CreateOrUpdatePublisherRequest request) {
        PublisherEntity publisherEntity = publisherRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Publisher not exist")
        );
        if (!Objects.equals(publisherEntity.getName(), request.name())) {
            publisherRepository.findPublisherByName(request.name()).ifPresent(entity -> {
                throw new BadRequestException("Publisher name " + request.name() + " is using, you can't use it");
            });
        }
        publisherEntity.setName(request.name());
        return publisherMapper.mapEntityToDto(publisherEntity);
    }
}
