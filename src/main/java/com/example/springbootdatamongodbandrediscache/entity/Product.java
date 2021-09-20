package com.example.springbootdatamongodbandrediscache.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

  private List<String> colors;

  private Date createdDate;

  @Id
  private String id;

  private Date lastModifiedDate;

  private String name;

  private double price;

  private int stock;
}
