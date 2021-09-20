package com.gdn.springdatamongodb.service.impl;

import com.gdn.springdatamongodb.repository.OrderRepository;
import com.gdn.springdatamongodb.service.ReportService;
import com.gdn.springdatamongodb.web.response.report.ProductSalesReportResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

  private final OrderRepository orderRepository;

  public ReportServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  @Cacheable(value = "report")
  public List<ProductSalesReportResponse> getAll() {
    try {
      Thread.sleep(5000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return orderRepository.getAllProduct();
  }
}
