package com.example.springbootdatamongodbandrediscache.web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

  @GetMapping
  public String getAll() {
    return "getAll";
  }
}
