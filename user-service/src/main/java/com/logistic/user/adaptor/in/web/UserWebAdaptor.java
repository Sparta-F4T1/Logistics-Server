package com.logistic.user.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.user.adaptor.in.web.request.TestRequest;
import com.logistic.user.application.port.in.UserUseCase;
import com.logistic.user.application.port.in.command.TestCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WebAdapter
@RequiredArgsConstructor
public class UserWebAdaptor {
  private final UserUseCase useCase;

  @PostMapping("/test")
  public ResponseEntity<ApiResponse<String>> test(@RequestBody TestRequest request){
    // request -> command -> useCase -> port -> repository
    TestCommand command = new TestCommand(request.id());
    String result = useCase.createUser(command);
    ApiResponse<String> response = ApiResponse.success("hi");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
