package com.example.springbootdatamongodbandrediscache.web.controller;

import com.example.springbootdatamongodbandrediscache.service.ProductService;
import com.example.springbootdatamongodbandrediscache.web.request.SaveProductRequest;
import com.example.springbootdatamongodbandrediscache.web.request.UpdateProductRequest;
import com.example.springbootdatamongodbandrediscache.web.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @DeleteMapping(value = "/{productId}")
  public ProductResponse delete(@PathVariable String productId) {
    return productService.delete(productId);
  }

  @GetMapping
  public Page<ProductResponse> getAll(@RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    return productService.getAll(PageRequest.of(page, size));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ProductResponse save(@RequestBody SaveProductRequest request) {
    return productService.save(request);
  }

  @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ProductResponse update(@PathVariable String productId, @RequestBody UpdateProductRequest request) {
    request.setProductId(productId);
    return productService.update(request);
  }
}
