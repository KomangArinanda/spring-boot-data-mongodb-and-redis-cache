package com.example.springbootdatamongodbandrediscache.service;

import com.example.springbootdatamongodbandrediscache.web.request.SaveProductRequest;
import com.example.springbootdatamongodbandrediscache.web.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Page<ProductResponse> getAll(Pageable pageable);

  ProductResponse save(SaveProductRequest request);
}
