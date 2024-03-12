package com.valeria.shop.controller;

import com.valeria.shop.dto.BrandDTO;
import com.valeria.shop.request.CreateOrUpdateBrandRequest;
import com.valeria.shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping()
    public List<BrandDTO> getAllBrands() {
        return brandService.getAllBrands();
    }

    @PostMapping
    public BrandDTO addNewBrand(@RequestBody CreateOrUpdateBrandRequest request) {
        return brandService.addNewBrand(request);
    }

    @DeleteMapping(path = "{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }

    @PutMapping(path = "{id}")
    public BrandDTO updateBrand(@PathVariable Long id, @RequestBody CreateOrUpdateBrandRequest request) {
        return brandService.updateBrand(id, request);
    }
}
