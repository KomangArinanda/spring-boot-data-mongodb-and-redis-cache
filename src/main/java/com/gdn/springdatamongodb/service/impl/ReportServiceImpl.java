package com.gdn.springdatamongodb.service.impl;

import com.gdn.springdatamongodb.repository.OrderRepository;
import com.gdn.springdatamongodb.service.ReportService;
import com.gdn.springdatamongodb.web.response.report.ProductSalesReportResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

  private final OrderRepository orderRepository;

  public ReportServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public List<ProductSalesReportResponse> getAll() {
    return orderRepository.getAllProduct();
  }
}
