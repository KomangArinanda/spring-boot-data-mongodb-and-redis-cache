package com.gdn.springdatamongodb.repository;

import com.gdn.springdatamongodb.web.response.report.ProductSalesReportResponse;

import java.util.List;

public interface OrderRepositoryCustom {

  List<ProductSalesReportResponse> getAllProduct();
}
