package com.projects.retail.bulk_order.event;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Builder
@Data
public class OrderCreatedEvent {
    private UUID orderId;
    private String email;
    private double totalAmount;
}
