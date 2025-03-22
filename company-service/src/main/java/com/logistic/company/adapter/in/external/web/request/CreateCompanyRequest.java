package com.logistic.company.adapter.in.external.web.request;

import com.logistic.company.domain.model.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateCompanyRequest(
    @NotBlank String name,
    @NotNull CompanyType type,
    @NotBlank String road,
    @NotNull Long hubId,
    @NotEmpty List<String> userIdList) {

}