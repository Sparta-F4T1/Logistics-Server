package com.logistic.product.application.port.out;

import java.util.List;

public interface ProductLockPort {
  void lock(Long productId);

  void lockAll(List<Long> productId);

  void unLock(List<Long> productId);
}
