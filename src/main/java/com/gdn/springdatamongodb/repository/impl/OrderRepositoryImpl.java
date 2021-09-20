package com.gdn.springdatamongodb.repository.impl;

import com.gdn.springdatamongodb.entity.Order;
import com.gdn.springdatamongodb.repository.OrderRepositoryCustom;
import com.gdn.springdatamongodb.web.response.report.ProductSalesReportResponse;
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
  public List<ProductSalesReportResponse> getAllProduct() {
    UnwindOperation unwindOperation = Aggregation.unwind("productDetails");

    AggregationExpression multiplyExpression = ArithmeticOperators.Multiply.valueOf("productDetails.quantity")
        .multiplyBy("productDetails.price");

    GroupOperation groupOperation = Aggregation.group("productDetails._id")
        .sum(multiplyExpression)
        .as("sales")
        .sum("productDetails.quantity")
        .as("quantity")
        .first("productDetails._id")
        .as("id")
        .first("productDetails.name")
        .as("name");

    SortOperation sortOperation = new SortOperation(Sort.by(Sort.Direction.DESC, "sales"));

    Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation);

    return mongoTemplate.aggregate(aggregation, Order.class, ProductSalesReportResponse.class)
        .getMappedResults();
  }
}
