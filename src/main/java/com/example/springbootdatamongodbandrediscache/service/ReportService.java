package com.example.springbootdatamongodbandrediscache.service;

import com.example.springbootdatamongodbandrediscache.web.response.ProductSalesReportResponse;

import java.util.List;

public interface ReportService {

  List<ProductSalesReportResponse> getAll();
}
