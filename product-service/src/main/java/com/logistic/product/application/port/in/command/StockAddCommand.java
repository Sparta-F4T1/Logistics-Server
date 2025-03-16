package com.logistic.product.application.port.in.command;

public record StockAddCommand(Long productId, Integer quantity) {
}
