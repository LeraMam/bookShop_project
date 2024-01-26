/*
package com.valeria.bookshop.service;

import com.valeria.bookshop.db.entity.DeliveryEntity;
import com.valeria.bookshop.db.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }
    public List<DeliveryEntity> getAllDelivery() {
        return deliveryRepository.findAll();
    }

    public void addNewDelivery(DeliveryEntity delivery) {
        deliveryRepository.save(delivery);
    }

    public void deleteDelivery(Long deliveryId) {
        deliveryRepository.deleteById(deliveryId);
    }

    @Transactional
    public void updateDelivery(Long deliveryId, DeliveryEntity newDelivery) {
        DeliveryEntity delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new IllegalStateException("delivery with id " + deliveryId + " does not exist")
        );
        if(newDelivery.getPostIndex() != null && !newDelivery.getPostIndex().isEmpty() && !Objects.equals(delivery.getPostIndex(), newDelivery.getPostIndex())) {
            delivery.setPostIndex(newDelivery.getPostIndex());
        }
        if(newDelivery.getPhone() != null && !newDelivery.getPhone().isEmpty() && !Objects.equals(delivery.getPhone(), newDelivery.getPhone())) {
            delivery.setPhone(newDelivery.getPhone());
        }
        if(newDelivery.getRegion() != null && !newDelivery.getRegion().isEmpty() && !Objects.equals(delivery.getRegion(), newDelivery.getRegion())) {
            delivery.setRegion(newDelivery.getRegion());
        }
        if(newDelivery.getCity() != null && !newDelivery.getCity().isEmpty() && !Objects.equals(delivery.getCity(), newDelivery.getCity())) {
            delivery.setCity(newDelivery.getCity());
        }
        if(newDelivery.getStreet() != null && !newDelivery.getStreet().isEmpty() && !Objects.equals(delivery.getStreet(), newDelivery.getStreet())) {
            delivery.setStreet(newDelivery.getStreet());
        }
        if(newDelivery.getHouse() != null && !newDelivery.getHouse().isEmpty() && !Objects.equals(delivery.getHouse(), newDelivery.getHouse())) {
            delivery.setHouse(newDelivery.getHouse());
        }
        if(newDelivery.getFlat() != null && !newDelivery.getFlat().isEmpty() && !Objects.equals(delivery.getFlat(), newDelivery.getFlat())) {
            delivery.setFlat(newDelivery.getFlat());
        }

    }
}
*/
