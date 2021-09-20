package com.example.springbootdatamongodbandrediscache.web.response;

import com.example.springbootdatamongodbandrediscache.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

  private Date date;

  private String id;

  private List<Order.ProductOrderDetail> productDetails;

  private double totalPayment;
}
