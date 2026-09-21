package com.projects.retail.bulk_order.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductResponseDTO {

    @NonNull
    private UUID productId;

    @NonNull
    private String productName;

    @NonNull
    private String productCode;

    private String productDesc;

    @NonNull
    private long stockQty;

    @NonNull
    private double price;

}