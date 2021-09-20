package com.gdn.springdatamongodb.repository;

import com.gdn.springdatamongodb.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Test
  void decreaseProductStock() {
    int initialStock = 100;
    int stockDecrement = 9;

    Product product = Product.builder()
        .stock(initialStock)
        .build();
    Product savedProduct = productRepository.save(product);

    Assertions.assertThat(savedProduct.getStock())
        .isEqualTo(initialStock);

    productRepository.decreaseProductStock(savedProduct.getId(), stockDecrement);

    Integer updatedStock = productRepository.findById(savedProduct.getId())
        .map(Product::getStock)
        .orElse(null);

    Assertions.assertThat(updatedStock)
        .isEqualTo(initialStock - stockDecrement);
  }

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
  }
}
