package com.example.springbootdatamongodbandrediscache.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSalesReportResponse implements Serializable {

  private String id;

  private String name;

  private int quantity;

  private double sales;
}
