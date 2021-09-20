package com.example.springbootdatamongodbandrediscache.service;

import com.example.springbootdatamongodbandrediscache.web.request.CreateOrderRequest;
import com.example.springbootdatamongodbandrediscache.web.response.OrderResponse;

import java.util.List;

public interface OrderService {

  OrderResponse create(List<CreateOrderRequest> request);
}
