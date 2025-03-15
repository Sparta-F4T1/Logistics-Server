package com.logistic.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.logistic.product.application.port.in.command.ProductCreateCommand;
import com.logistic.product.application.port.out.CompanyClientPort;
import com.logistic.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductServiceTest {

  @Autowired
  private ProductService productService;
  @MockitoBean
  private CompanyClientPort companyClientPort;

  @DisplayName("상품 생성이 성공한다.")
  @Test
  void create_success() {
    // given
    ProductCreateCommand command = new ProductCreateCommand("상품이름", 100, 1L);
    Mockito.when(companyClientPort.existsById(Mockito.anyLong())).thenReturn(true);
    // when
    Product product = productService.createProduct(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getId()).isEqualTo(1L);
  }
}