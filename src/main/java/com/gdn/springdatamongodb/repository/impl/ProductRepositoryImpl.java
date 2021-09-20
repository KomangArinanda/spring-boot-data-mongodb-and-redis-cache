package com.gdn.springdatamongodb.repository.impl;

import com.gdn.springdatamongodb.entity.Product;
import com.gdn.springdatamongodb.repository.ProductRepositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void decreaseProductStock(String productId, int decrement) {
    Query query = Query.query(Criteria.where("_id")
        .is(productId)
        .and("stock")
        .gte(decrement));
    Update update = new Update().inc("stock", (-1) * decrement);
    mongoTemplate.updateFirst(query, update, Product.class);
  }
}
