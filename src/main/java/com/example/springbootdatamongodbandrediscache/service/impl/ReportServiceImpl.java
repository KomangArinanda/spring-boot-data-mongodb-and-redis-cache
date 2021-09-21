package com.example.springbootdatamongodbandrediscache.service.impl;

import com.example.springbootdatamongodbandrediscache.repository.OrderRepository;
import com.example.springbootdatamongodbandrediscache.service.ReportService;
import com.example.springbootdatamongodbandrediscache.web.response.ProductSalesReportResponse;
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
    return orderRepository.getAllReport();
  }
}
