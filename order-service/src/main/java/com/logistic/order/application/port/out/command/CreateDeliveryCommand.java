package com.logistic.order.application.port.out.command;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CreateDeliveryCommand implements Serializable{
    Long orderId;
    Long departCompanyId;
    Long arrivalCompanyId;
    Long departHubId;
    Long arrivalHubId;
}
