package com.projects.retail.bulk_order.event;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Builder
public class PaymentResultEvent {

    private UUID orderId;
    private UUID transactionId;
    private boolean success;
    private String message;

}
