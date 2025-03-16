package com.logistic.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.logistic.product.application.port.in.command.ProductCreateCommand;
import com.logistic.product.application.port.in.command.ProductDeleteCommand;
import com.logistic.product.application.port.in.command.ProductInfoUpdateCommand;
import com.logistic.product.application.port.in.command.StockAddCommand;
import com.logistic.product.application.port.in.command.StockDecreaseCommand;
import com.logistic.product.application.port.in.command.StockUpdateCommand;
import com.logistic.product.application.port.out.CompanyClientPort;
import com.logistic.product.application.port.out.ProductPersistencePort;
import com.logistic.product.domain.Product;
import java.util.Optional;
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
  @Autowired
  private ProductPersistencePort productPersistencePort;

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
    assertThat(product.getId()).isNotNull();
  }

  @DisplayName("상품 정보수정이 성공한다.")
  @Test
  void updateInfo_success() {
    // given
    Product saved = saveProduct();
    ProductInfoUpdateCommand command = new ProductInfoUpdateCommand(saveProduct().getId(), "업데이트");
    // when
    Product product = productService.updateProductInfo(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getInfo().getName()).isEqualTo("업데이트");
  }

  @DisplayName("상품 재고수정이 성공한다.")
  @Test
  void updateStock_success() {
    // given
    Product saved = saveProduct();
    StockUpdateCommand command = new StockUpdateCommand(saveProduct().getId(), 1000);
    // when
    Product product = productService.updateStock(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getStock().getQuantity()).isEqualTo(1000);
  }

  @DisplayName("상품 소프트삭제가 성공한다.")
  @Test
  void softDelete_success() {
    // given
    Product saved = saveProduct();
    ProductDeleteCommand command = new ProductDeleteCommand(saved.getId());
    // when
    productService.deleteProduct(command);
    Optional<Product> findProduct = productPersistencePort.findById(saved.getId());
    Product product = findProduct.orElse(null);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getIsDeleted()).isTrue();
  }

  @DisplayName("재고 추가가 성공한다.")
  @Test
  void decreaseStock_success() {
    // given
    Product saved = saveProduct();
    StockAddCommand command = new StockAddCommand(saved.getId(), 100);
    // when
    Product product = productService.addStock(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getStock().getQuantity()).isEqualTo(200);
  }

  @DisplayName("재고 차감이 성공한다.")
  @Test
  void addStock_success() {
    // given
    Product saved = saveProduct();
    StockDecreaseCommand command = new StockDecreaseCommand(saved.getId(), 100);
    // when
    Product product = productService.decreaseStock(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getStock().getQuantity()).isEqualTo(0);
  }

  private Product saveProduct() {
    Product product = Product.create("상품1", 100, 1L);
    return productPersistencePort.save(product);
  }
}