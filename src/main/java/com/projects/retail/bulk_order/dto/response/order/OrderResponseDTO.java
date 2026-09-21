package com.projects.retail.bulk_order.dto.response.order;

import com.projects.retail.bulk_order.entity.OrderItemEntity;
import com.projects.retail.bulk_order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderResponseDTO {

    @NonNull
    private UUID orderId;
    @NonNull
    private String orderEmail;
    @NonNull
    private OrderStatus orderStatus;
    @NonNull
    private double totalAmount;
    private List<OrderItemEntity> items;

}
