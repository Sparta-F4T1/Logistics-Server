package com.logistic.driver.adapter.in.external.web.request;


import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateDriverRequest(
    @NotBlank String driverId,
    @NotNull DriverType type,
    @NotNull DriverStatus status,
    @NotNull List<Long> hubIds) {
}
