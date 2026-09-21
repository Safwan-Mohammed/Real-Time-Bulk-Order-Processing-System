package com.projects.retail.bulk_order.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NonNull
    private UUID productId;
    @NonNull
    private long quantity;
    @NonNull
    private double unitPrice;
}
