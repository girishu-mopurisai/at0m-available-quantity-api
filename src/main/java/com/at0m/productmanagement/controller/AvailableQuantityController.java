package com.at0m.productmanagement.controller;

import com.at0m.common.model.AvailableQuantity;
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

    @GetMapping("/quantity")
    public List<AvailableQuantity> getAllAvailableQuantity(){
        return getAvailableQuantityService.getAllList();
    }

    @PostMapping("/quantity")
    public String saveQuantityWithProduct(@RequestBody List<AvailableQuantity> quantityWithProducts){
        return getAvailableQuantityService.saveAll(quantityWithProducts);
    }
}
