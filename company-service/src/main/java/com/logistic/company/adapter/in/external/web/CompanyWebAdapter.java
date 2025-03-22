package com.logistic.company.adapter.in.external.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.passport.annotation.WithPassport;
import com.logistic.common.passport.model.Passport;
import com.logistic.common.response.ApiResponse;
import com.logistic.company.adapter.in.external.web.mapper.CompanyWebMapper;
import com.logistic.company.adapter.in.external.web.request.CreateCompanyRequest;
import com.logistic.company.adapter.in.external.web.request.SearchCompanyRequest;
import com.logistic.company.adapter.in.external.web.request.UpdateCompanyRequest;
import com.logistic.company.adapter.in.external.web.response.CommandCompanyResponse;
import com.logistic.company.adapter.in.external.web.response.QueryCompanyResponse;
import com.logistic.company.application.port.in.CompanyCommandUseCase;
import com.logistic.company.application.port.in.CompanyQueryUseCase;
import com.logistic.company.domain.model.Company;
import com.logistic.company.domain.model.CompanyView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyWebAdapter {
  private final CompanyCommandUseCase companyCommandUseCase;
  private final CompanyWebMapper companyWebMapper;
  private final CompanyQueryUseCase companyQueryUseCase;


  @PostMapping
  public ResponseEntity<ApiResponse<CommandCompanyResponse>> createCompany(
      @Valid @RequestBody final CreateCompanyRequest request,
      @WithPassport final Passport passport) {
    final Company company = companyCommandUseCase.createCompany(
        companyWebMapper.toCreateCommand(request, passport));
    final ApiResponse<CommandCompanyResponse> response = ApiResponse.success(companyWebMapper.toResponse(company));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{companyId}")
  public ResponseEntity<ApiResponse<CommandCompanyResponse>> updateCompany(
      @PathVariable final Long companyId,
      @RequestBody final UpdateCompanyRequest request,
      @WithPassport final Passport passport) {
    final Company company = companyCommandUseCase.updateCompany(
        companyWebMapper.toUpdateCommand(companyId, request, passport));
    final ApiResponse<CommandCompanyResponse> response = ApiResponse.success(companyWebMapper.toResponse(company));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{companyId}")
  public ResponseEntity<ApiResponse<Void>> deleteCompany(
      @PathVariable final Long companyId,
      @WithPassport final Passport passport) {
    companyCommandUseCase.deleteCompany(
        companyWebMapper.toDeleteCommand(companyId, passport));
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
  }

  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<QueryCompanyResponse>> findCompany(
      @PathVariable final Long companyId,
      @WithPassport final Passport passport) {
    final CompanyView companyView = companyQueryUseCase.findCompany(
        companyWebMapper.toFindQuery(companyId, passport));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(companyWebMapper.toQueryResponse(companyView)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<QueryCompanyResponse>>> search(
      @ModelAttribute final SearchCompanyRequest request,
      @PageableDefault final Pageable pageable,
      @WithPassport final Passport passport) {
    final Page<QueryCompanyResponse> response = companyQueryUseCase.search(
            companyWebMapper.toSearchQuery(request, pageable, passport))
        .map(companyWebMapper::toQueryResponse);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(response));
  }
}