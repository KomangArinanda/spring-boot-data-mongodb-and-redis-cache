package com.gdn.springdatamongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  private List<String> colors;

  @CreatedDate
  private Date createdDate;

  @Id
  private String id;

  @LastModifiedDate
  private Date lastModifiedDate;

  private String name;

  private double price;

  private int stock;

  @Version
  private Long version;
}
