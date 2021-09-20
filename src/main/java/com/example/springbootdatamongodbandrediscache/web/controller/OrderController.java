package com.example.springbootdatamongodbandrediscache.web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public String create() {
    return "create";
  }
}
