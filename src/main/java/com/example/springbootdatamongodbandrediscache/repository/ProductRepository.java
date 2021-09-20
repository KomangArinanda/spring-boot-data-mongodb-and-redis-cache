package com.example.springbootdatamongodbandrediscache.repository;

import com.example.springbootdatamongodbandrediscache.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {}
