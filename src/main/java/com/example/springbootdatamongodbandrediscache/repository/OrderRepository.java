package com.example.springbootdatamongodbandrediscache.repository;

import com.example.springbootdatamongodbandrediscache.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {}
