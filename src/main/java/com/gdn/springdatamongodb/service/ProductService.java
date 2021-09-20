package com.gdn.springdatamongodb.service;

import com.gdn.springdatamongodb.web.request.product.SaveProductRequest;
import com.gdn.springdatamongodb.web.request.product.UpdateProductRequest;
import com.gdn.springdatamongodb.web.response.product.ProductResponse;

import java.util.List;

public interface ProductService {

  ProductResponse delete(String productId);

  List<ProductResponse> getAll();

  ProductResponse save(SaveProductRequest request);

  ProductResponse update(UpdateProductRequest request);
}
