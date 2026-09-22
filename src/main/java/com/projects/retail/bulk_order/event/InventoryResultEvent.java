package com.projects.retail.bulk_order.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResultEvent {

    private UUID orderId;
    private boolean success;
    private String message;
}