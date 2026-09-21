package com.projects.retail.bulk_order.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NonNull
    private String orderEmail;
    @NonNull
    private double totalAmount;
    @NonNull
    private List<OrderItemRequestDTO> items;
}
