package com.logistic.product.application.port.in.command;

public record ProductCreateCommand(String name, Integer stock, Long companyId) {
}
