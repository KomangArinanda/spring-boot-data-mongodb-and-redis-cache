package com.gdn.springdatamongodb.service.impl;

import com.gdn.springdatamongodb.entity.Product;
import com.gdn.springdatamongodb.repository.ProductRepository;
import com.gdn.springdatamongodb.service.ProductService;
import com.gdn.springdatamongodb.web.request.product.SaveProductRequest;
import com.gdn.springdatamongodb.web.request.product.UpdateProductRequest;
import com.gdn.springdatamongodb.web.response.product.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductResponse delete(String productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid productId : " + productId));
    productRepository.deleteById(productId);

    return toProductResponse(product);
  }

  @Override
  public List<ProductResponse> getAll() {
    return productRepository.findAll()
        .stream()
        .map(this::toProductResponse)
        .collect(Collectors.toList());
  }

  @Override
  public ProductResponse save(SaveProductRequest request) {
    Product product = toProduct(request);
    Product savedProduct = productRepository.save(product);

    return toProductResponse(savedProduct);
  }

  @Override
  public ProductResponse update(UpdateProductRequest request) {
    Product product = productRepository.findById(request.getProductId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid productId : " + request.getProductId()));

    product.setColors(request.getColors());
    product.setName(request.getName());
    product.setPrice(request.getPrice());

    Product updatedProduct = productRepository.save(product);
    return toProductResponse(updatedProduct);
  }

  private Product toProduct(SaveProductRequest request) {
    return Product.builder()
        .colors(request.getColors())
        .name(request.getName())
        .price(request.getPrice())
        .stock(request.getStock())
        .build();
  }

  private ProductResponse toProductResponse(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .stock(product.getStock())
        .build();
  }
}
