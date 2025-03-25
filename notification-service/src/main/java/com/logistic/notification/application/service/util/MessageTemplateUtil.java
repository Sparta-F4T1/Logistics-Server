package com.logistic.notification.application.service.util;

import com.logistic.notification.application.port.in.command.SlackMessageCreateCommand;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTemplateUtil {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 a h시");

  private static final String SLACK_MESSAGE_TEMPLATE = """
        주문 번호 : %d
        주문자 정보 : %s / %s
        상품 정보 : %s %d개
        발송지 : %s
        경유지 : %s
        도착지 : %s
        배송담당자 : %s / %s
        
        위 내용을 기반으로 도출된 최종 발송 시한은 %s 입니다.
        """;

  public static String createSlackMessage(SlackMessageCreateCommand command) {
    String departHubName = command.hubNames().get(0);
    String HubRouteNames = String.join(", ", command.hubNames().subList(1, command.hubNames().size() - 1));
    return String.format(SLACK_MESSAGE_TEMPLATE,
        command.orderId(),
        command.userName(),
        command.slackId(),
        command.productName(),
        command.quantity(),
        departHubName,
        HubRouteNames,
        command.CompanyAddress(),
        command.driverName(),
        command.driver(),
        creatTimeFormat(command.deliveryDeadLine())
    );
  }

  private static String creatTimeFormat(LocalDateTime deliveryDeadline) {
    return deliveryDeadline.format(DATE_FORMATTER);
  }
}
