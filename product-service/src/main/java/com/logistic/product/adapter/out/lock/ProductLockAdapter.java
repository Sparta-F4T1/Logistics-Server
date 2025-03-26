package com.logistic.product.adapter.out.lock;

import com.logistic.common.annotation.Adapter;
import com.logistic.product.adapter.out.lock.util.RedisKeyFactory;
import com.logistic.product.application.port.out.ProductLockPort;
import com.logistic.product.domain.exception.CustomBadRequestException.ProductLockException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

@Adapter
@Slf4j(topic = "ProductLockAdapter")
@RequiredArgsConstructor
public class ProductLockAdapter implements ProductLockPort {
  private final RedissonClient redissonClient;

  @Override
  public void lock(Long productId) {

    RLock lock = redissonClient.getLock(RedisKeyFactory.getLockKey(productId));
    try {
      boolean isLocked = lock.tryLock(1000, 30, TimeUnit.SECONDS);
      if (!isLocked) {
        throw new ProductLockException("락 획득에 실패했습니다.");
      }
    } catch (InterruptedException e) {
      log.error("락 획득 중 인터럽트 발생", e);
      throw new RuntimeException("락 처리 중 인터럽트가 발생했습니다.", e);
    }
  }

  @Override
  public void lockAll(final List<Long> productIds) {
    for (Long productId : productIds) {
      RLock lock = redissonClient.getLock(RedisKeyFactory.getLockKey(productId));
      try {
        boolean isLocked = lock.tryLock(1000, 30, TimeUnit.SECONDS);
        if (!isLocked) {
          throw new ProductLockException("락 일부 획득에 실패했습니다.");
        }
      } catch (InterruptedException e) {
        log.error("락 일부 획득 중 인터럽트 발생", e);
        throw new RuntimeException("락 처리 중 인터럽트가 발생했습니다.", e);
      }
    }
    log.info("모든 락 획득 완료 {}", productIds);
  }

  @Override
  public void unLock(final List<Long> productIds) {
    for (Long productId : productIds) {
      RLock lock = redissonClient.getLock(RedisKeyFactory.getLockKey(productId));
      try {
        if (lock.isHeldByCurrentThread()) {
          lock.unlock();
        }
      } catch (Exception e) {
        log.error("락 일부 해제 중 오류 발생", e);
        throw new RuntimeException("락 일부 해제 중 오류 발생", e);
      }
    }
    log.info("모든 락 해제 완료 {}", productIds);
  }
}
