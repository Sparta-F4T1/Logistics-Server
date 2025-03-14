package com.logistic.company.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.company.adaptor.in.web.mapper.CompanyWebMapper;
import com.logistic.company.adaptor.in.web.request.CompanyCreateRequest;
import com.logistic.company.adaptor.in.web.response.CompanyResponseDto;
import com.logistic.company.application.port.in.CompanyUseCase;
import com.logistic.company.domain.Company;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyWebAdaptor {
  private final CompanyUseCase companyUseCase;
  private final CompanyWebMapper companyWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<CompanyResponseDto>> createCompany(
      @Valid @RequestBody CompanyCreateRequest request) {
    Company company = companyUseCase.createCompany(companyWebMapper.toCreateCommand(request));
    ApiResponse<CompanyResponseDto> response = ApiResponse.success(companyWebMapper.toResponseDto(company));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}