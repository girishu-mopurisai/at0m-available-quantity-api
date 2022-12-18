package com.at0m.productmanagement.service;

import com.at0m.common.model.AvailableQuantity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAvailableQuantityService {

    private final MongoTemplate mongoTemplate;

    public GetAvailableQuantityService(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    public List<AvailableQuantity> getAllList(){
        return mongoTemplate.findAll(AvailableQuantity.class);
    }

    public String saveAll(List<AvailableQuantity> quantityWithProducts){
        for(int i=0;i<quantityWithProducts.size();i++){
            mongoTemplate.save(quantityWithProducts.get(i));
        }
        return "Saved All";
    }
}
