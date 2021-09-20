package com.gdn.springdatamongodb.repository;

public interface ProductRepositoryCustom {

  void decreaseProductStock(String productId, int decrement);
}
