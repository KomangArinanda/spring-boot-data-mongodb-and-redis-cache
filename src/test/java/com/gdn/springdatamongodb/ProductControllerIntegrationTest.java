package com.gdn.springdatamongodb;

import com.gdn.springdatamongodb.entity.Product;
import com.gdn.springdatamongodb.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = {"/application.properties"})
class ProductControllerIntegrationTest {

  private static final String PRODUCT_NAME_1 = "product-name-1";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProductRepository productRepository;

  @Test
  void delete() throws Exception {
    Product product1 = Product.builder()
        .colors(Collections.singletonList("red"))
        .name(PRODUCT_NAME_1)
        .stock(10)
        .price(5_000)
        .build();
    Product savedProduct = productRepository.save(product1);

    Assertions.assertThat(productRepository.existsById(savedProduct.getId()))
        .isTrue();

    mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + savedProduct.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status()
            .isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(PRODUCT_NAME_1)));

    Assertions.assertThat(productRepository.findAll())
        .isEmpty();
  }

  @Test
  void getAll() throws Exception {
    Product product1 = Product.builder()
        .colors(Collections.singletonList("red"))
        .name(PRODUCT_NAME_1)
        .stock(10)
        .price(5_000)
        .build();
    productRepository.save(product1);

    mockMvc.perform(MockMvcRequestBuilders.get("/products")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status()
            .isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(PRODUCT_NAME_1)));
  }

  @Test
  void save() throws Exception {
    String requestBody =
        "{ \"colors\":[\"red\", \"blue\", \"black\"], \"name\": \"product-name\", \"price\": 10000, \"stock\": 100 }";
    mockMvc.perform(MockMvcRequestBuilders.post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status()
            .isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("product-name")));

    List<String> productNamesInDatabase = productRepository.findAll()
        .stream()
        .map(Product::getName)
        .collect(Collectors.toList());

    Assertions.assertThat(productNamesInDatabase)
        .containsOnly("product-name");
  }

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
  }

  @Test
  void update() throws Exception {
    Product product1 = Product.builder()
        .colors(Collections.singletonList("red"))
        .name(PRODUCT_NAME_1)
        .stock(10)
        .price(5_000)
        .build();
    Product savedProduct = productRepository.save(product1);

    Assertions.assertThat(productRepository.existsById(savedProduct.getId()))
        .isTrue();

    String requestBody = "{ \"colors\":[\"red\", \"blue\", \"black\"], \"name\": \"product-name\", \"price\": 10001 }";
    mockMvc.perform(MockMvcRequestBuilders.put("/products/" + savedProduct.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status()
            .isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("product-name")));

    List<String> productNamesInDatabase = productRepository.findAll()
        .stream()
        .map(Product::getName)
        .collect(Collectors.toList());

    Assertions.assertThat(productNamesInDatabase)
        .containsOnly("product-name");
  }

  @Test
  void update_productNotExists() throws Exception {
    String requestBody = "{ \"colors\":[\"red\", \"blue\", \"black\"], \"name\": \"product-name\", \"price\": 10001 }";
    mockMvc.perform(MockMvcRequestBuilders.put("/products/non-exists-product-id")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status()
            .isBadRequest());
  }
}

