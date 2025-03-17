package com.logistic.company.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.company.adaptor.in.web.mapper.CompanyWebMapper;
import com.logistic.company.adaptor.in.web.request.CompanyCreateRequest;
import com.logistic.company.adaptor.in.web.request.CompanyUpdateRequest;
import com.logistic.company.adaptor.in.web.response.CompanyResponse;
import com.logistic.company.application.port.in.CompanyUseCase;
import com.logistic.company.domain.Company;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyWebAdaptor {
  private final CompanyUseCase companyUseCase;
  private final CompanyWebMapper companyWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(
      @Valid @RequestBody final CompanyCreateRequest request) {
    final Company company = companyUseCase.createCompany(companyWebMapper.toCreateCommand(request));
    final ApiResponse<CompanyResponse> response = ApiResponse.success(companyWebMapper.toResponse(company));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{companyId}")
  public ResponseEntity<ApiResponse<CompanyResponse>> updateCompany(
      @PathVariable final Long companyId,
      @RequestBody final CompanyUpdateRequest request) {
    final Company company = companyUseCase.updateCompany(companyWebMapper.toUpdateCommand(companyId, request));
    final ApiResponse<CompanyResponse> response = ApiResponse.success(companyWebMapper.toResponse(company));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{companyId}")
  public ResponseEntity<ApiResponse<Void>> deleteCompany(
      @PathVariable final Long companyId) {
    companyUseCase.deleteCompany(companyWebMapper.toDeleteCommand(companyId));
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null));
  }
}