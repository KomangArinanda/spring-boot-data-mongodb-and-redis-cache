package com.gdn.springdatamongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SpringDataMongodbApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDataMongodbApplication.class, args);
  }
}
