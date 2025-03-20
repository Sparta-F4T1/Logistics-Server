package com.logistic.auth.adapter.out.support.jwt.mapper;

import com.logistic.auth.domain.service.TokenValidationResult;
import io.jsonwebtoken.Claims;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthJwtMapper {

  @Mapping(target = "tokenId", expression = "java(TokenId.of(claims.getId()))")
  @Mapping(target = "userId", expression = "java(UserId.of(claims.getSubject()))")
  @Mapping(target = "issuedAt", expression = "java(claims.getIssuedAt().toInstant())")
  @Mapping(target = "expiration", expression = "java(claims.getExpiration().toInstant())")
  TokenValidationResult toTokenValidationResult(Claims claims);
}