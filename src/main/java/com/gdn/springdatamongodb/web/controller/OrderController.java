package com.gdn.springdatamongodb.web.controller;

import com.gdn.springdatamongodb.service.OrderService;
import com.gdn.springdatamongodb.web.request.order.CreateOrderRequest;
import com.gdn.springdatamongodb.web.response.order.OrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public OrderResponse create(@RequestBody List<CreateOrderRequest> request) {
    return orderService.createOrder(request);
  }
}
