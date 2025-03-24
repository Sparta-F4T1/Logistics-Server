package com.logistic.driver.adapter.out.message.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

  private static final String EXCHANGE = "DRIVER";
  private static final String QUEUE_DELIVERY = "ASSIGNED_DRIVER_DELIVERY";
  private static final String QUEUE_NOTIFICATION = "ASSIGNED_DRIVER_NOTIFICATION";

  @Bean
  Queue queueDelivery() {
    return new Queue(QUEUE_DELIVERY);
  }

  @Bean
  Queue queueNotification() {
    return new Queue(QUEUE_NOTIFICATION);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(EXCHANGE);
  }

  @Bean
  Binding bindingDelivery() {
    return BindingBuilder.bind(queueDelivery()).to(exchange()).with(QUEUE_DELIVERY);
  }

  @Bean
  Binding bindingNotification() {
    return BindingBuilder.bind(queueNotification()).to(exchange()).with(QUEUE_NOTIFICATION);
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    return rabbitTemplate;
  }
}
