package com.gdn.springdatamongodb.service;

import com.gdn.springdatamongodb.web.request.order.CreateOrderRequest;
import com.gdn.springdatamongodb.web.response.order.OrderResponse;

import java.util.List;

public interface OrderService {

  OrderResponse createOrder(List<CreateOrderRequest> request);
}
