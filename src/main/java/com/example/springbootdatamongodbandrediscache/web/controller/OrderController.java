package com.example.springbootdatamongodbandrediscache.web.controller;

import com.example.springbootdatamongodbandrediscache.service.OrderService;
import com.example.springbootdatamongodbandrediscache.web.request.CreateOrderRequest;
import com.example.springbootdatamongodbandrediscache.web.response.OrderResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public OrderResponse create(@RequestBody List<CreateOrderRequest> requests) {
    return orderService.create(requests);
  }
}
