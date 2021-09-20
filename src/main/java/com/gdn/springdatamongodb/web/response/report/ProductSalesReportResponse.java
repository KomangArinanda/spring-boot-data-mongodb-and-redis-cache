package com.gdn.springdatamongodb.web.response.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSalesReportResponse {

  private String id;

  private String name;

  private int quantity;

  private double sales;
}
