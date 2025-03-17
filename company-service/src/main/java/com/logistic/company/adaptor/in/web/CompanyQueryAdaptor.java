package com.logistic.company.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.company.adaptor.in.web.mapper.CompanyWebMapper;
import com.logistic.company.adaptor.in.web.request.CompanySearchRequest;
import com.logistic.company.adaptor.in.web.response.CompanyResponse;
import com.logistic.company.application.port.in.CompanyQueryUseCase;
import com.logistic.company.domain.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyQueryAdaptor {
  private final CompanyQueryUseCase companyQueryUseCase;
  private final CompanyWebMapper companyWebMapper;

  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<CompanyResponse>> findCompany(
      @PathVariable final Long companyId) {
    final Company company = companyQueryUseCase.findCompany(companyWebMapper.toFindQuery(companyId));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(companyWebMapper.toResponse(company)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<CompanyResponse>>> search(
      @ModelAttribute final CompanySearchRequest request,
      @PageableDefault final Pageable pageable) {
    final Page<CompanyResponse> response = companyQueryUseCase.search(companyWebMapper.toSearchQuery(request, pageable))
        .map(companyWebMapper::toResponse);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(response));
  }
}
