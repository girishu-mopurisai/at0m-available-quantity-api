package com.at0m.productmanagement.util;

import com.at0m.common.model.AvailableQuantity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ControllerUtil {

    public void removeDuplicatesFromList(List<AvailableQuantity> availableQuantity){
        Set<AvailableQuantity> setOfQuantities = new HashSet<>(availableQuantity);
        availableQuantity.clear();
        availableQuantity.addAll(setOfQuantities);
    }

    public Query createQuery(String productName){
        return Query.query(Criteria.where("productName").is(productName));
    }

    public Update createUpdateQuery(AvailableQuantity availableQuantity){
        Update update = (new Update()
                .set("productName",availableQuantity.getProductName())
                .set("quantityAvailable",availableQuantity.getQuantityAvailable())
                .set("modifiedDate",new Date()));
        return update;
    }
}
