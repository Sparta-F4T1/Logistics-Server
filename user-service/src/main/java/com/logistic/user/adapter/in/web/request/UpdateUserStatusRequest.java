package com.logistic.user.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserStatusRequest(@NotBlank(message = "변경 상태는 필수 입력 값입니다.") String status) {
}