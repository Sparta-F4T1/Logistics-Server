package com.logistic.ai.adapter.out.external.ai;

import com.logistic.ai.adapter.out.external.ai.dto.GPTRequest;
import com.logistic.ai.adapter.out.external.ai.dto.GPTResponse;
import com.logistic.ai.application.port.out.ChatGPTPort;
import com.logistic.common.annotation.Adapter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

@Slf4j
@Adapter
@RequiredArgsConstructor
public class ChatGPTAdapter implements ChatGPTPort {
  @Value("${openai.model}")
  private String model;

  @Value("${openai.url}")
  private String apiURL;

  @Value("${openai.prompt}")
  private String devPrompt;

  @Value("${openai.maxToken}")
  private int maxToken;

  private final RestClient restClient;

  public String getResponse(
      LocalDateTime orderCreatedAt,
      Integer totalHubDeliveryTime,
      String arrivalHubAddress,
      String arrivalCompanyAddress
  ) {
    String prompt = getPrompt(orderCreatedAt, totalHubDeliveryTime, arrivalHubAddress, arrivalCompanyAddress);
    GPTRequest request = new GPTRequest(model, devPrompt, prompt, maxToken);
    GPTResponse response = restClient.post()
        .uri(apiURL)
        .body(request)
        .retrieve()
        .body(GPTResponse.class);
    return response.choices().get(0).message().content();
  }

  private String getPrompt(
      LocalDateTime orderCreatedAt,
      Integer totalHubDeliveryTime,
      String arrivalHubAddress,
      String arrivalCompanyAddress
  ){
    StringBuilder prompt = new StringBuilder();
    prompt.append(String.format("orderCreatedAt : %s", orderCreatedAt)).append(" / ");
    prompt.append(String.format("totalHubDeliveryTime : %d", totalHubDeliveryTime)).append(" / ");
    prompt.append(String.format("arrivalHubAddress : %s", arrivalHubAddress)).append(" / ");
    prompt.append(String.format("arrivalCompanyAddress : %s", arrivalCompanyAddress));
    return prompt.toString();
  }
}
