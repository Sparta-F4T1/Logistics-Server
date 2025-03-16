package com.logistic.product.application.port.in.command;

public record StockDecreaseCommand(Long productId, Integer quantity) {
}
