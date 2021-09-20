package com.example.springbootdatamongodbandrediscache.service.impl;

import com.example.springbootdatamongodbandrediscache.entity.Product;
import com.example.springbootdatamongodbandrediscache.repository.ProductRepository;
import com.example.springbootdatamongodbandrediscache.service.ProductService;
import com.example.springbootdatamongodbandrediscache.web.request.SaveProductRequest;
import com.example.springbootdatamongodbandrediscache.web.request.UpdateProductRequest;
import com.example.springbootdatamongodbandrediscache.web.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductResponse delete(String productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid productId: " + productId));

    productRepository.deleteById(productId);

    return toProductResponse(product);
  }

  @Override
  public Page<ProductResponse> getAll(Pageable pageable) {
    return productRepository.findAll(pageable)
        .map(this::toProductResponse);
  }

  @Override
  public ProductResponse save(SaveProductRequest request) {
    Product savedProduct = productRepository.save(toProduct(request));
    return toProductResponse(savedProduct);
  }

  @Override
  public ProductResponse update(UpdateProductRequest request) {
    Product product = productRepository.findById(request.getProductId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid productId: " + request.getProductId()));

    product.setColors(request.getColors());
    product.setPrice(request.getPrice());
    product.setName(request.getName());

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
