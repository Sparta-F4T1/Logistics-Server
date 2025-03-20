package com.logistic.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.logistic.product.application.port.in.command.AddStockCommand;
import com.logistic.product.application.port.in.command.CreateProductCommand;
import com.logistic.product.application.port.in.command.DecreaseStockCommand;
import com.logistic.product.application.port.in.command.DeleteProductCommand;
import com.logistic.product.application.port.in.command.UpdateProductCommand;
import com.logistic.product.application.port.out.ProductInternalPort;
import com.logistic.product.application.port.out.ProductPersistencePort;
import com.logistic.product.application.service.dto.CompanyInfo;
import com.logistic.product.domain.Product;
import com.logistic.product.domain.command.ProductForCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
  private ProductPersistencePort productPersistencePort;

  @DisplayName("상품 생성이 성공한다.")
  @Test
  void create_success() {
    // given
    CompanyInfo mockCompanyInfo = new CompanyInfo(1L);
    when(productInternalPort.findCompany(anyLong(), any())).thenReturn(mockCompanyInfo);
    CreateProductCommand command = new CreateProductCommand("상품이름", 100, 1L, null);
    // when
    Product product = productService.createProduct(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getId()).isNotNull();
  }

  @DisplayName("상품 수정이 성공한다.")
  @Test
  void updateInfo_success() {
    // given
    UpdateProductCommand command = new UpdateProductCommand(saveProduct().getId(), "업데이트", 100, null);
    // when
    Product product = productService.updateProduct(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getInfo().getName()).isEqualTo("업데이트");
  }

  @DisplayName("상품 삭제가 성공한다.")
  @Test
  void softDelete_success() {
    // given
    Product saved = saveProduct();
    DeleteProductCommand command = new DeleteProductCommand(saved.getId(), null);
    // when
    productService.deleteProduct(command);
    Product product = productPersistencePort.findById(saved.getId());

    // then
    assertThat(product).isNotNull();
    assertThat(product.getIsDeleted()).isTrue();
  }

  @DisplayName("재고 추가가 성공한다.")
  @Test
  void decreaseStock_success() {
    // given
    Product saved = saveProduct();
    AddStockCommand command = new AddStockCommand(saved.getId(), 100, null);
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
    DecreaseStockCommand command = new DecreaseStockCommand(saved.getId(), 100, null);
    // when
    Product product = productService.decreaseStock(command);
    // then
    assertThat(product).isNotNull();
    assertThat(product.getStock().getQuantity()).isEqualTo(0);
  }

  private Product saveProduct() {
    ProductForCreate forCreate = new ProductForCreate("상품", 100, 1L);
    Product product = Product.create(forCreate);
    return productPersistencePort.save(product);
  }
}