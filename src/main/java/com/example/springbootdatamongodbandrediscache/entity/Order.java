package com.example.springbootdatamongodbandrediscache.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

  @CreatedDate
  private Date date;

  @Id
  private String id;

  private List<ProductOrderDetail> productDetails;

  private double totalPayment;

  @Version
  private Long version;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ProductOrderDetail {

    private String color;

    private String id;

    private String name;

    private double price;

    private int quantity;
  }
}
