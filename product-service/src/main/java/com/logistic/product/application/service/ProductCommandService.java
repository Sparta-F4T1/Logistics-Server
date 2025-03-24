package com.logistic.product.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.product.application.port.in.ProductCommandUseCase;
import com.logistic.product.application.port.in.command.CreateProductCommand;
import com.logistic.product.application.port.in.command.DeleteProductCommand;
import com.logistic.product.application.port.in.command.UpdateProductCommand;
import com.logistic.product.application.port.in.command.UpdateStockCommand;
import com.logistic.product.application.port.out.ProductCommandPersistencePort;
import com.logistic.product.application.port.out.ProductInternalPort;
import com.logistic.product.application.port.out.ProductLockPort;
import com.logistic.product.domain.Product;
import com.logistic.product.domain.ProductPolicyService;
import com.logistic.product.domain.command.ProductForCreate;
import com.logistic.product.domain.command.ProductForUpdate;
import com.logistic.product.domain.event.StockLockReleaseEvent;
import com.logistic.product.domain.vo.Company;
import com.logistic.product.domain.vo.Hub;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Slf4j(topic = "ProductCommandService")
@Transactional
@RequiredArgsConstructor
public class ProductCommandService implements ProductCommandUseCase {
  private final ProductLockPort lockPort;
  private final ProductInternalPort internalPort;
  private final ProductPolicyService policyService;
  private final ApplicationEventPublisher eventPublisher;
  private final ProductCommandPersistencePort persistencePort;

  @Override
  public Product createProduct(final CreateProductCommand command) {
    final Company company = findCompany(command.companyId());
    final Hub hub = findHub(company.hubId());
    policyService.validateCreateProduct(command.passport(), hub, company);
    final ProductForCreate forCreate = command.toForCreate(company);
    final Product product = Product.create(forCreate);
    return persistencePort.save(product);
  }

  @Override
  public Product updateProduct(final UpdateProductCommand command) {
    try {
      lock(command.productId());
      Product product = findProduct(command.productId());
      final Company company = findCompany(product.getCompanyId());
      final Hub hub = findHub(company.hubId());
      policyService.validateUpdateProduct(command.passport(), hub, company);
      final ProductForUpdate forUpdate = new ProductForUpdate(command.name(), command.quantity());
      product.update(forUpdate);
      return persistencePort.save(product);
    } finally {
      eventPublisher.publishEvent(new StockLockReleaseEvent(this, List.of(command.productId())));
    }
  }

  @Override
  public void decreaseStock(final UpdateStockCommand command) {
    final Map<Long, Integer> stockMap = command.stockMap();
    List<Long> productIds = stockMap.keySet().stream().toList();
    try {
      lockAll(productIds);
      stockMap.forEach((productId, quantity) -> {
        Product product = findProduct(productId);
        Integer origin = product.getStock().getQuantity();
        product.decreaseStock(quantity);
        persistencePort.save(product);
        Integer updated = product.getStock().getQuantity();
        log.info("재고 차감 로직 완료 productId:{},기존 재고: {} ,남은 재고:{} ", product.getId(), origin, updated);
      });
    } finally {
      eventPublisher.publishEvent(new StockLockReleaseEvent(this, productIds));
    }
  }

  @Override
  public void increaseStock(UpdateStockCommand command) {
    final Map<Long, Integer> stockMap = command.stockMap();
    List<Long> productIds = stockMap.keySet().stream().toList();
    try {
      lockAll(productIds);
      stockMap.forEach((productId, quantity) -> {
        Product product = findProduct(productId);
        Integer origin = product.getStock().getQuantity();
        product.increaseStock(quantity);
        persistencePort.save(product);
        Integer updated = product.getStock().getQuantity();
        log.info("재고 복구 로직 완료 productId:{},기존 재고: {} ,남은 재고:{} ", product.getId(), origin, updated);
      });
    } finally {
      eventPublisher.publishEvent(new StockLockReleaseEvent(this, productIds));
    }
  }

  @Override
  public void deleteProduct(final DeleteProductCommand command) {
    Product product = findProduct(command.productId());
    final Company company = findCompany(product.getCompanyId());
    final Hub hub = findHub(company.hubId());
    policyService.validateDeleteProduct(command.passport(), hub);
    product.delete();
    persistencePort.save(product);
  }

  private Product findProduct(final Long productId) {
    return persistencePort.findById(productId);
  }

  private Company findCompany(final Long companyId) {
    return internalPort.findCompany(companyId);
  }

  private Hub findHub(final Long hubId) {
    return internalPort.findHub(hubId);
  }

  private void lock(final Long productId) {
    lockPort.lock(productId);
  }

  private void lockAll(final List<Long> productIds) {
    lockPort.lockAll(productIds);
  }
}
