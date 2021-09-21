package com.example.springbootdatamongodbandrediscache.service.impl;

import com.example.springbootdatamongodbandrediscache.entity.Order;
import com.example.springbootdatamongodbandrediscache.entity.Product;
import com.example.springbootdatamongodbandrediscache.repository.OrderRepository;
import com.example.springbootdatamongodbandrediscache.repository.ProductRepository;
import com.example.springbootdatamongodbandrediscache.service.OrderService;
import com.example.springbootdatamongodbandrediscache.web.request.CreateOrderRequest;
import com.example.springbootdatamongodbandrediscache.web.response.OrderResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

  private static final String ORDER_ID_PREFIX = "ORDER/";

  private final OrderRepository orderRepository;

  private final ProductRepository productRepository;

  public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  @Override
  @CacheEvict(cacheNames = "report", allEntries = true)
  @Transactional
  public OrderResponse create(List<CreateOrderRequest> request) {
    List<Order.ProductOrderDetail> productDetails = toProductOrderDetails(request);

    double totalPayment = productDetails.stream()
        .map(productDetail -> productDetail.getPrice() * productDetail.getQuantity())
        .mapToDouble(Double::doubleValue)
        .sum();

    Order order = Order.builder()
        .id(ORDER_ID_PREFIX + System.currentTimeMillis())
        .productDetails(productDetails)
        .totalPayment(totalPayment)
        .build();

    Order savedOrder = orderRepository.save(order);

    decrementProductsStock(productDetails);

    return toOrderResponse(savedOrder);
  }

  private void decrementProductsStock(List<Order.ProductOrderDetail> productDetails) {
    productDetails.forEach(product -> {
      productRepository.decreaseProductStock(product.getId(), product.getQuantity());
    });
  }

  private OrderResponse toOrderResponse(Order order) {
    return OrderResponse.builder()
        .date(order.getDate())
        .id(order.getId())
        .productDetails(order.getProductDetails())
        .totalPayment(order.getTotalPayment())
        .build();
  }

  private Order.ProductOrderDetail toProductOrderDetail(CreateOrderRequest request) {
    Product product = productRepository.findByIdAndStockGreaterThanEqual(request.getProductId(), request.getQuantity());

    if (product == null) {
      throw new IllegalArgumentException("Invalid Product Id : " + request.getProductId());
    }

    return Order.ProductOrderDetail.builder()
        .color(request.getColor())
        .id(request.getProductId())
        .name(product.getName())
        .price(product.getPrice())
        .quantity(request.getQuantity())
        .build();
  }

  private List<Order.ProductOrderDetail> toProductOrderDetails(List<CreateOrderRequest> request) {
    return request.stream()
        .map(this::toProductOrderDetail)
        .collect(Collectors.toList());
  }
}
