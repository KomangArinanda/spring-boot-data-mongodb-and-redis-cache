package com.gdn.springdatamongodb.web.request.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveProductRequest {

  private List<String> colors;

  private String name;

  private double price;

  private int stock;
}
