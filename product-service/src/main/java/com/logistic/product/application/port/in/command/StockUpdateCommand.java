package com.logistic.product.application.port.in.command;

public record StockUpdateCommand(Long productId, Integer quantity) {
}
