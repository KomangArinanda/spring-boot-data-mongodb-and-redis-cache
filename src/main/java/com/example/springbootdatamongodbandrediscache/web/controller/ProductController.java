package com.example.springbootdatamongodbandrediscache.web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

  @DeleteMapping(value = "/{productId}")
  public String delete(@PathVariable String productId) {
    return "delete";
  }

  @GetMapping
  public String getAll(@RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    return "getAll";
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public String save() {
    return "save";
  }

  @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public String update(@PathVariable String productId) {
    return "update";
  }
}
