package com.gdn.springdatamongodb.web.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

  private String id;

  private String name;

  private double price;

  private int stock;
}
