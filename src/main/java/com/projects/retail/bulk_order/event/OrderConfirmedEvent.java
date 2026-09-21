package com.projects.retail.bulk_order.event;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Builder
public class OrderConfirmedEvent {

    private UUID orderId;
    private String email;
    private double totalAmount;
}
