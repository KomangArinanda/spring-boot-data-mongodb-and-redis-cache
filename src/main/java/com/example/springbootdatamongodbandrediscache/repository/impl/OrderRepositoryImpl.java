package com.example.springbootdatamongodbandrediscache.repository.impl;

import com.example.springbootdatamongodbandrediscache.entity.Order;
import com.example.springbootdatamongodbandrediscache.repository.OrderRepositoryCustom;
import com.example.springbootdatamongodbandrediscache.web.response.ProductSalesReportResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  public OrderRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<ProductSalesReportResponse> getAllReport() {
    UnwindOperation unwindOperation = Aggregation.unwind("productDetails");

    AggregationExpression multiplyExpression = ArithmeticOperators.Multiply.valueOf("productDetails.price")
        .multiplyBy("productDetails.quantity");

    GroupOperation groupOperation = Aggregation.group("productDetails._id")
        .first("productDetails.name")
        .as("name")
        .first("productDetails._id")
        .as("id")
        .sum(multiplyExpression)
        .as("sales")
        .sum("productDetails.quantity")
        .as("quantity");

    SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "sales");

    Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation);

    return mongoTemplate.aggregate(aggregation, Order.class, ProductSalesReportResponse.class)
        .getMappedResults();
  }
}
