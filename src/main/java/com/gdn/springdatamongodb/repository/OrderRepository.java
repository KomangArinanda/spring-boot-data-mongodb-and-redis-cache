package com.gdn.springdatamongodb.repository;

import com.gdn.springdatamongodb.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String>, OrderRepositoryCustom {}
