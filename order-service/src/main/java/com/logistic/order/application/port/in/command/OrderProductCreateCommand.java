package com.logistic.order.application.port.in.command;

public record OrderProductCreateCommand(
    Long productId,
    int amount
) {
}
