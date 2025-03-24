package com.logistic.product.adapter.out.lock.util;

public class RedisKeyFactory {
  public static final String LOCK_KEY_FORMAT = "product:lockAll:%s";

  public static String getLockKey(final Long productId) {
    return String.format(LOCK_KEY_FORMAT, productId);
  }
}
