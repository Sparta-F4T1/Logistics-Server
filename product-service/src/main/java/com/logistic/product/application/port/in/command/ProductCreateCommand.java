package com.logistic.product.application.port.in.command;

public record ProductCreateCommand(String name, Integer quantity, Long companyId) {
}
