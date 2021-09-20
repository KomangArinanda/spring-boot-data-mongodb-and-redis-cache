package com.gdn.springdatamongodb;

import com.gdn.springdatamongodb.entity.Order;
import com.gdn.springdatamongodb.repository.OrderRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = {"/application.properties"})
class ReportControllerIntegrationTest {

  private static final String PRODUCT_ID_1 = "product-id-1";

  private static final String PRODUCT_ID_2 = "product-id-2";

  private static final String PRODUCT_NAME_1 = "product-name-1";

  private static final String PRODUCT_NAME_2 = "product-name-2";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private OrderRepository orderRepository;

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

  @Test
  void getAll() throws Exception {
    Order order1 = Order.builder()
        .productDetails(Arrays.asList(Order.ProductOrderDetail.builder()
            .id(PRODUCT_ID_1)
            .price(10_000)
            .quantity(9)
            .name(PRODUCT_NAME_1)
            .build(), Order.ProductOrderDetail.builder()
            .id(PRODUCT_ID_2)
            .price(8_000)
            .quantity(10)
            .name(PRODUCT_NAME_2)
            .build()))
        .build();
    Order order2 = Order.builder()
        .productDetails(Collections.singletonList(Order.ProductOrderDetail.builder()
            .id(PRODUCT_ID_2)
            .price(9_000)
            .quantity(5)
            .name(PRODUCT_NAME_2)
            .build()))
        .build();
    orderRepository.saveAll(Arrays.asList(order1, order2));

    mockMvc.perform(MockMvcRequestBuilders.get("/reports")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status()
            .isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(PRODUCT_ID_2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is(PRODUCT_NAME_2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].quantity", Matchers.is(15)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].sales", Matchers.is(125_000D)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id", Matchers.is(PRODUCT_ID_1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name", Matchers.is(PRODUCT_NAME_1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[1].quantity", Matchers.is(9)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[1].sales", Matchers.is(90_000D)));
  }
}
