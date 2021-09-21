package com.gdn.springdatamongodb.service;

import com.gdn.springdatamongodb.entity.Product;
import com.gdn.springdatamongodb.repository.ProductRepository;
import com.gdn.springdatamongodb.service.impl.ProductServiceImpl;
import com.gdn.springdatamongodb.web.request.product.SaveProductRequest;
import com.gdn.springdatamongodb.web.response.product.ProductResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  private static final String PRODUCT_ID_1 = "product-id";

  private static final String PRODUCT_NAME_1 = "product-name";

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductServiceImpl productService;

  @Test
  void save() {
    SaveProductRequest request = SaveProductRequest.builder()
        .colors(Arrays.asList("abc"))
        .name(PRODUCT_NAME_1)
        .build();

    Product product = Product.builder()
        .colors(Arrays.asList("abc"))
        .name(PRODUCT_NAME_1)
        .build();

    given(productRepository.save(product)).willReturn(product);

    ProductResponse expectedResponse = ProductResponse.builder()
        .name(PRODUCT_NAME_1)
        .build();

    ProductResponse response = productService.save(request);

    assertThat(response).isEqualTo(expectedResponse);

    then(productRepository).should()
        .save(product);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(productRepository);
  }
}
