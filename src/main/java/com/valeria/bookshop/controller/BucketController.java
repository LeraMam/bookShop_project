package com.valeria.bookshop.controller;

import com.valeria.bookshop.additional_classes.BuckerReadingRequest;
import com.valeria.bookshop.db.entity.BucketState;
import com.valeria.bookshop.db.entity.DeliveryEntity;
import com.valeria.bookshop.dto.BucketDTO;
import com.valeria.bookshop.request.BucketActionRequest;
import com.valeria.bookshop.request.ChangeBucketStateRequest;
import com.valeria.bookshop.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bucket")
@RequiredArgsConstructor
public class BucketController {
    private final BucketService bucketService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/action")
    public void applyBucketAction(@RequestBody BucketActionRequest request) {
        bucketService.applyBucketAction(request);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/submit")
    public void submitBucket(@RequestBody DeliveryEntity deliveryEntity) {
        bucketService.submitBucket(deliveryEntity);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("")
    public BucketDTO getCurrentBucket() {
        System.out.println(bucketService.getCurrentBucket());
        return bucketService.getCurrentBucket();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/all")
    public List<BucketDTO> getAllBucketsForUser() {
        /*List<BucketDTO> backets = bucketService.getBucketsForCurrentUser();
        BuckerReadingRequest buckerReadingRequest = new BuckerReadingRequest();
        List<String> states = new ArrayList<>();
        states.add((String)BucketState.OPEN);
        buckerReadingRequest.setBuckets(backets);*/
        return bucketService.getBucketsForCurrentUser();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/unique")
    public List<BucketDTO> getAllBucketsForAnalyticsUser() {
        return bucketService.getAllBucketsForAnalyticsUser();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/analyticsCategories")
    public List<BucketDTO> getAllBucketsForAnalytics() {
        List<BucketDTO> backets = bucketService.getBucketsForAllUsers();
        System.out.println(backets);
        return backets;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public List<BucketDTO> getAllByState(@RequestParam BucketState state) {
        return bucketService.getBuckets(state);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{bucketId}")
    public void changeBucketStateToProcess(@PathVariable Long bucketId, @RequestBody ChangeBucketStateRequest request) {
        bucketService.changeBucketState(bucketId, request.state());
    }
}
