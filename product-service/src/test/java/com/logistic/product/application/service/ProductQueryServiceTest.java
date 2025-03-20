package com.logistic.product.application.service;

import com.logistic.product.application.port.in.query.FindProductQuery;
import com.logistic.product.application.port.in.query.SearchProductQuery;
import com.logistic.product.application.port.out.ProductInternalPort;
import com.logistic.product.application.port.out.ProductPersistencePort;
import com.logistic.product.domain.Product;
import com.logistic.product.domain.command.ProductForCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductQueryServiceTest {
  @Autowired
  private ProductQueryService productQueryService;
  @MockitoBean
  private ProductInternalPort productInternalPort;
  @Autowired
  private ProductPersistencePort productPersistencePort;

  @DisplayName("상품 상세조회가 성공한다.")
  @Test
  void findOne_success() {
    // given
    Product saved = saveProduct();
    // when
    FindProductQuery findQuery = new FindProductQuery(saved.getId(), null);
    Product product = productQueryService.findProduct(findQuery);
    // then
    Assertions.assertThat(product.getId()).isEqualTo(saved.getId());
  }

  @DisplayName("상품 목록조회가 성공한다.")
  @Test
  void search_success() {
    // given
    saveProducts();
    // when
    Pageable pageable = PageRequest.of(0, 10);
    SearchProductQuery query = new SearchProductQuery(1L, "상품", pageable, null);
    Page<Product> search = productQueryService.search(query);
    // then
    Assertions.assertThat(search).isNotNull();
    Assertions.assertThat(search.getTotalElements()).isEqualTo(10);
  }

  private Product saveProduct() {
    ProductForCreate forCreate = new ProductForCreate("상품", 100, 1L);
    Product product = Product.create(forCreate);
    return productPersistencePort.save(product);
  }

  private void saveProducts() {
    for (int i = 0; i < 10; i++) {
      saveProduct();
    }
  }
}