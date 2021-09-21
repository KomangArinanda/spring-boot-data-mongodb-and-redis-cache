package com.example.springbootdatamongodbandrediscache.repository;

import com.example.springbootdatamongodbandrediscache.web.response.ProductSalesReportResponse;

import java.util.List;

public interface OrderRepositoryCustom {

  List<ProductSalesReportResponse> getAllReport();
}
