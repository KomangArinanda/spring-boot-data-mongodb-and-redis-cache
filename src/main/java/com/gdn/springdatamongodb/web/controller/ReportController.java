package com.gdn.springdatamongodb.web.controller;

import com.gdn.springdatamongodb.service.ReportService;
import com.gdn.springdatamongodb.web.response.report.ProductSalesReportResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

  private final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping
  public List<ProductSalesReportResponse> getAll() {
    return reportService.getAll();
  }
}
