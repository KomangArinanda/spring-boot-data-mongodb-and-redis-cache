package com.gdn.springdatamongodb.web.controller;

import com.gdn.springdatamongodb.service.ProductService;
import com.gdn.springdatamongodb.web.request.product.SaveProductRequest;
import com.gdn.springdatamongodb.web.request.product.UpdateProductRequest;
import com.gdn.springdatamongodb.web.response.product.ProductResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
  public List<ProductResponse> getAll() {
    return productService.getAll();
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