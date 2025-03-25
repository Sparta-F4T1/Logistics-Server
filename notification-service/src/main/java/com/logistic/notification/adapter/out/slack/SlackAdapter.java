package com.logistic.notification.adapter.out.slack;

import com.logistic.common.annotation.Adapter;
import com.logistic.notification.adapter.out.slack.request.SlackRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Adapter
@RequiredArgsConstructor
public class SlackAdapter {
  private final RestTemplate slackRestTemplate;

  @Value("${slack.hook.url}")
  public String url;

  @Transactional
  public void sendMessage(String userId, String message) {
    try{
      SlackRequest request = new SlackRequest(userId, message);
      slackRestTemplate.postForEntity(url, request, String.class);
    }catch (Exception e){
      // todo : 예외처리
    }
  }

}
