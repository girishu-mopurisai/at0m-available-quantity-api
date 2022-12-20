package com.at0m.productmanagement.service;

import com.at0m.common.model.AvailableQuantity;
import com.at0m.productmanagement.util.ControllerUtil;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.bulk.BulkWriteError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Slf4j
public class GetAvailableQuantityService {

    private final MongoTemplate mongoTemplate;
    private final ControllerUtil controllerUtil;

    public GetAvailableQuantityService(MongoTemplate mongoTemplate, ControllerUtil controllerUtil){
        this.mongoTemplate = mongoTemplate;
        this.controllerUtil = controllerUtil;
    }

    public List<AvailableQuantity> getAllList(){
        return mongoTemplate.findAll(AvailableQuantity.class);
    }

    public List<AvailableQuantity> saveAll(List<AvailableQuantity> quantityWithProducts){
        BulkOperations bulkOps = this.mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, AvailableQuantity.class);
        controllerUtil.removeDuplicatesFromList(quantityWithProducts);
        Iterator iterator = quantityWithProducts.iterator();
        while(iterator.hasNext()){
            AvailableQuantity availableQuantity = (AvailableQuantity) iterator.next();
            Query query = controllerUtil.createQuery(availableQuantity.getProductName());
            Update update = controllerUtil.createUpdateQuery(availableQuantity);
            bulkOps.upsert(query,update);
        }
        try {
            bulkOps.execute();
        } catch(DuplicateKeyException duplicateKeyException) {
            Throwable cause = duplicateKeyException.getCause();
            if(cause instanceof MongoBulkWriteException) {
                MongoBulkWriteException mongoBulkWriteException = (MongoBulkWriteException) cause;
                List<BulkWriteError> bulkWriteErrors = mongoBulkWriteException.getWriteErrors();
                bulkWriteErrors.forEach(bulkWriteError -> {
                    log.error(bulkWriteError.getIndex() + " Is already present in Database");
                });
            }
        }
        return quantityWithProducts;
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
