package com.gdn.springdatamongodb.service.impl;

import com.gdn.springdatamongodb.entity.Order;
import com.gdn.springdatamongodb.entity.Product;
import com.gdn.springdatamongodb.repository.OrderRepository;
import com.gdn.springdatamongodb.repository.ProductRepository;
import com.gdn.springdatamongodb.service.OrderService;
import com.gdn.springdatamongodb.web.request.order.CreateOrderRequest;
import com.gdn.springdatamongodb.web.response.order.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  public static final String ORDER_ID_PREFIX = "ORDER/";

  private final OrderRepository orderRepository;

  private final ProductRepository productRepository;

  public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  @Override
//  @Transactional
  public OrderResponse createOrder(List<CreateOrderRequest> request) {
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

    log.info("Order is saved " + savedOrder);

    request.forEach(createOrderRequest -> {
      productRepository.decreaseProductStock(createOrderRequest.getProductId(), createOrderRequest.getQuantity());
    });

    return toOrderResponse(savedOrder);
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
      throw new IllegalArgumentException("Invalid productId : " + request.getProductId());
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
