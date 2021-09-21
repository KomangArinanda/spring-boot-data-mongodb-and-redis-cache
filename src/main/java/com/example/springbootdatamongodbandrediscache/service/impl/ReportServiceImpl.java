package com.example.springbootdatamongodbandrediscache.service.impl;

import com.example.springbootdatamongodbandrediscache.repository.OrderRepository;
import com.example.springbootdatamongodbandrediscache.service.ReportService;
import com.example.springbootdatamongodbandrediscache.web.response.ProductSalesReportResponse;
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
  @Cacheable(cacheNames = "report")
  public List<ProductSalesReportResponse> getAll() {
    try {
      Thread.sleep(5000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return orderRepository.getAllReport();
  }
}
