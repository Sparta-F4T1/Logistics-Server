package com.logistic.order.adaptor.in.web.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateOrderRequest(
    @NotNull(message = "판매자는 필수입니다.") Long sellerId,
    @NotNull(message = "구매자는 필수입니다.") Long buyerId,
    @Size(max = 50, message = "요처상항은 50자이내 작성합시시오.") String memo,
    @NotNull List<OrderProduct> orderProducts) {

  public record OrderProduct(
      @NotEmpty(message = "품목은 필수입니다.") Long productId,
      @Min(1) Integer quantity) {
  }
}
