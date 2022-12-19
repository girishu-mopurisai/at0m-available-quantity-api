package com.at0m.productmanagement.service;

import com.at0m.common.model.AvailableQuantity;
import com.at0m.common.model.Product;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class GetAvailableQuantityService {

    private final MongoTemplate mongoTemplate;

    public GetAvailableQuantityService(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    public List<AvailableQuantity> getAllList(){
        return mongoTemplate.findAll(AvailableQuantity.class);
    }

    public List<AvailableQuantity> saveAll(List<AvailableQuantity> quantityWithProducts){
        List<AvailableQuantity> savedObjects = new ArrayList<>();
        for(int i=0;i<quantityWithProducts.size();i++){
            AvailableQuantity availableQuantity = mongoTemplate.save(quantityWithProducts.get(i));
            savedObjects.add(availableQuantity);
        }
        return savedObjects;
    }

    public AvailableQuantity getByProductName(String productName) {
        List<AvailableQuantity> availableQuantityByName = mongoTemplate.find(query(where("productName").is(productName)), AvailableQuantity.class);
        if(!availableQuantityByName.isEmpty()){
            return availableQuantityByName.get(0);
        }
        else{
            return null;
        }
    }
}
