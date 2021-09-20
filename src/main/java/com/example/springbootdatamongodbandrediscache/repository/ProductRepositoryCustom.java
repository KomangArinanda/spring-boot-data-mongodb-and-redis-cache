package com.example.springbootdatamongodbandrediscache.repository;

public interface ProductRepositoryCustom {

  void decreaseProductStock(String productId, int decrement);
}
