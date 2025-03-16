package com.logistic.product.application.port.in.command;

public record ProductInfoUpdateCommand(Long productId, String name) {
}
