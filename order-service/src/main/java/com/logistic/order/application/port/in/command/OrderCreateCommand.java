package com.logistic.order.application.port.in.command;

import java.util.List;

public record OrderCreateCommand(
    Long sellerId,
    Long buyerId,
    String memo,
    List<OrderProductCreateCommand> orderProducts
) {
}
