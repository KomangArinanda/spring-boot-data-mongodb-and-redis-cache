package com.gdn.springdatamongodb.service;

import com.gdn.springdatamongodb.web.request.product.SaveProductRequest;
import com.gdn.springdatamongodb.web.request.product.UpdateProductRequest;
import com.gdn.springdatamongodb.web.response.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  ProductResponse delete(String productId);

  Page<ProductResponse> getAll(Pageable pageable);

  ProductResponse save(SaveProductRequest request);

  ProductResponse update(UpdateProductRequest request);
}
