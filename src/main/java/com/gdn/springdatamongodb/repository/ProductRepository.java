package com.gdn.springdatamongodb.repository;

import com.gdn.springdatamongodb.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

  Product findByIdAndStockGreaterThanEqual(String productId, int quantity);
}
