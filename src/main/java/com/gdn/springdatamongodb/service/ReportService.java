package com.gdn.springdatamongodb.service;

import com.gdn.springdatamongodb.web.response.report.ProductSalesReportResponse;

import java.util.List;

public interface ReportService {

  List<ProductSalesReportResponse> getAll();
}
