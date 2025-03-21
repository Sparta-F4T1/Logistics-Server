package com.logistic.user.adapter.in.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(
    @NotBlank(message = "사용자 ID는 필수 입력값입니다.")
    String userId,

    @NotBlank(message = "이름은 필수 입력값입니다.")
    String name,

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    String password,

    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @NotBlank(message = "슬랙 계정은 필수 입력값입니다.")
    String slackAccount,

    @NotNull(message = "역할 ID는 필수 입력값입니다.")
    Long roleId,

    @NotBlank(message = "역할 이름은 필수 입력값입니다.")
    String roleName
) {
}