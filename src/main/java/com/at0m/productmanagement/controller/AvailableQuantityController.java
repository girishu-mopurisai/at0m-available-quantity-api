package com.at0m.productmanagement.controller;

import com.at0m.common.model.AvailableQuantity;
import com.at0m.common.model.ProductResponseResource;
import com.at0m.productmanagement.service.GetAvailableQuantityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AvailableQuantityController {

    private final GetAvailableQuantityService getAvailableQuantityService;

    public AvailableQuantityController(GetAvailableQuantityService getAvailableQuantityService) {
        this.getAvailableQuantityService = getAvailableQuantityService;
    }

    @GetMapping("/quantities")
    public List<AvailableQuantity> getAllAvailableQuantity(){
        return getAvailableQuantityService.getAllList();
    }

    @GetMapping("/quantity/{productName}")
    public AvailableQuantity getByProductname(@PathVariable String productName){
        return getAvailableQuantityService.getByProductName(productName);
    }

    @PostMapping("/quantities")
    public List<AvailableQuantity> saveListOfQuantities(@RequestBody List<AvailableQuantity> quantityWithProducts){
        return getAvailableQuantityService.saveAll(quantityWithProducts);
    }
}
