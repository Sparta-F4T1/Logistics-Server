package com.logistic.order.adapter.out.message.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingQueueConfig {
  @Value("${message.exchange}")
  private String exchange;

  @Value("${message.queue.delivery}")
  private String queueDelivery;

  @Value("${message.queue.product}")
  private String queueProduct;

  @Bean
  Queue queueDelivery(){
    return new Queue(queueDelivery);
  }

  @Bean
  Queue queueProduct(){
    return new Queue(queueProduct);
  }

  @Bean
  TopicExchange exchange(){
    return new TopicExchange(exchange);
  }

  @Bean
  Binding bindingDelivery(){
    return BindingBuilder.bind(queueDelivery()).to(exchange()).with(queueDelivery);
  }

  @Bean
  Binding bindingProduct(){
    return BindingBuilder.bind(queueProduct()).to(exchange()).with(queueProduct);
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    return rabbitTemplate;
  }
}
