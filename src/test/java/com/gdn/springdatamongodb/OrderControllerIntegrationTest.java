package com.gdn.springdatamongodb;

import com.gdn.springdatamongodb.entity.Product;
import com.gdn.springdatamongodb.repository.OrderRepository;
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

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = {"/application.properties"})
class OrderControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Test
  void create() throws Exception {
    Product product1 = Product.builder()
        .colors(Collections.singletonList("red"))
        .stock(10)
        .price(5_000)
        .build();
    Product savedProduct1 = productRepository.save(product1);

    Product product2 = Product.builder()
        .colors(Collections.singletonList("red"))
        .stock(15)
        .price(6_000)
        .build();
    Product savedProduct2 = productRepository.save(product2);

    String requestBody = String.format(
        "[ { \"color\": \"red\", \"productId\": \"%s\", \"quantity\": 2 }, { \"color\": \"red\", \"productId\": \"%s\", \"quantity\": 3 } ]",
        savedProduct1.getId(), savedProduct2.getId());
    mockMvc.perform(MockMvcRequestBuilders.post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status()
            .isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.totalPayment", Matchers.is(28_000D)));

    Assertions.assertThat(orderRepository.count())
        .isEqualTo(1L);

    Integer expectedStockProduct1 = productRepository.findById(savedProduct1.getId())
        .map(Product::getStock)
        .orElse(null);
    Integer expectedStockProduct2 = productRepository.findById(savedProduct2.getId())
        .map(Product::getStock)
        .orElse(null);

    Assertions.assertThat(expectedStockProduct1)
        .isEqualTo(8);
    Assertions.assertThat(expectedStockProduct2)
        .isEqualTo(12);
  }

  @Test
  void create_productNotExists() throws Exception {
    String requestBody = String.format(
        "[ { \"color\": \"red\", \"productId\": \"%s\", \"quantity\": 2 }, { \"color\": \"red\", \"productId\": \"%s\", \"quantity\": 3 } ]",
        "non-exists-product-id-1", "non-exists-product-id-2");
    mockMvc.perform(MockMvcRequestBuilders.post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status()
            .isBadRequest());
  }

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
    orderRepository.deleteAll();
  }
}
