package com.example.springbootdatamongodbandrediscache.service.impl;

import com.example.springbootdatamongodbandrediscache.repository.OrderRepository;
import com.example.springbootdatamongodbandrediscache.service.ReportService;
import com.example.springbootdatamongodbandrediscache.web.response.ProductSalesReportResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReportServiceImpl implements ReportService {

  private final OrderRepository orderRepository;

  private final RedisTemplate<Object, Object> redisTemplate;

  public ReportServiceImpl(OrderRepository orderRepository, RedisTemplate<Object, Object> redisTemplate) {
    this.orderRepository = orderRepository;
    this.redisTemplate = redisTemplate;
  }

  @Override
  //  @Cacheable(cacheNames = "report")
  //  @Cacheable(cacheNames = "report", key = "'myPrefix_'.concat(#paramName)")
  public List<ProductSalesReportResponse> getAll() {
    try {
      Thread.sleep(5000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    List<ProductSalesReportResponse> allReport = orderRepository.getAllReport();

    redisTemplate.opsForValue()
        .set("report1", allReport, 10, TimeUnit.MINUTES);

    return allReport;
  }
}
