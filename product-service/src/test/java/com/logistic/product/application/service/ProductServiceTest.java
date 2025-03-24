package com.logistic.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.logistic.common.passport.model.Passport;
import com.logistic.product.application.port.in.command.CreateProductCommand;
import com.logistic.product.application.port.in.command.DeleteProductCommand;
import com.logistic.product.application.port.in.command.UpdateProductCommand;
import com.logistic.product.application.port.in.command.UpdateStockCommand;
import com.logistic.product.application.port.out.ProductCommandPersistencePort;
import com.logistic.product.application.port.out.ProductInternalPort;
import com.logistic.product.domain.Product;
import com.logistic.product.domain.command.ProductForCreate;
import com.logistic.product.domain.vo.Company;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private ProductCommandService productService;
  @MockitoBean
  private ProductInternalPort productInternalPort;
  @Autowired
  private ProductCommandPersistencePort productCommandPersistencePort;

  @DisplayName("상품 생성이 성공한다.")
  @Test
  void create_success() {
    // given
    Company mockCompany = new Company(1L, "회사이름", 1L, List.of("user1", "user2"));
    when(productInternalPort.findCompany(anyLong())).thenReturn(mockCompany);
    CreateProductCommand command = new CreateProductCommand("상품이름", 100, 1L, null);
    // when
    Product product = productService.createProduct(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getId()).isNotNull();
  }

  @DisplayName("상품 수정이 성공한다.")
  @Test
  void update_Product_success() {
    // given
    UpdateProductCommand command = new UpdateProductCommand(saveProduct().getId(), "업데이트", 100, null);
    // when
    Product product = productService.updateProduct(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getName()).isEqualTo("업데이트");
  }

  @DisplayName("상품 삭제가 성공한다.")
  @Test
  void softDelete_success() {
    // given
    Product saved = saveProduct();
    DeleteProductCommand command = new DeleteProductCommand(saved.getId(), null);
    // when
    productService.deleteProduct(command);
    Product product = productCommandPersistencePort.findById(saved.getId());

    // then
    assertThat(product).isNotNull();
    assertThat(product.getIsDeleted()).isTrue();
  }

  @DisplayName("재고 차감이 성공한다.")
  @Test
  void decreaseStock_success() {
    // given
    Product saved = saveProduct();
    Product saved1 = saveProduct();
    Product saved2 = saveProduct();
    Passport passport = Mockito.mock(Passport.class);
    Map<Long, Integer> stockMap = new HashMap<>();
    stockMap.put(saved.getId(), 10);
    stockMap.put(saved1.getId(), 20);
    stockMap.put(saved2.getId(), 30);
    UpdateStockCommand command = new UpdateStockCommand(stockMap, passport);
    // when
    productService.decreaseStock(command);
    // then
    Product updated = productCommandPersistencePort.findById(saved.getId());
    Product updated1 = productCommandPersistencePort.findById(saved1.getId());
    Product updated2 = productCommandPersistencePort.findById(saved2.getId());
    assertThat(updated).isNotNull();
    assertThat(updated.getStock().getQuantity()).isEqualTo(90);
    assertThat(updated1.getStock().getQuantity()).isEqualTo(80);
    assertThat(updated2.getStock().getQuantity()).isEqualTo(70);
  }

  private Product saveProduct() {
    Company company = Mockito.mock(Company.class);
    ProductForCreate forCreate = new ProductForCreate("상품", 100, company);
    Product product = Product.create(forCreate);
    return productCommandPersistencePort.save(product);
  }
}