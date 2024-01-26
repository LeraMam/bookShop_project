/*
package com.valeria.bookshop.controller;

import com.valeria.bookshop.db.entity.DeliveryEntity;
import com.valeria.bookshop.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/get")
    public List<DeliveryEntity> getAllDelivery() {
        return deliveryService.getAllDelivery();
    }
    @PostMapping
    public void addNewDelivery(@RequestBody DeliveryEntity delivery){
        deliveryService.addNewDelivery(delivery);
    }
    @DeleteMapping(path= "{deliveryId}")
    public void deleteDelivery(@PathVariable("deliveryId") Long deliveryId){
        deliveryService.deleteDelivery(deliveryId);
    }

    @PutMapping(path= "{deliveryId}")
    public void updateDelivery(@PathVariable("deliveryId") Long deliveryId,
                               @RequestBody DeliveryEntity delivery){
        deliveryService.updateDelivery(deliveryId, delivery);
    }
}
*/
