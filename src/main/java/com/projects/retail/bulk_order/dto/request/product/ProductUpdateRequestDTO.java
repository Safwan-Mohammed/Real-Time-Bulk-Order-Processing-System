package com.projects.retail.bulk_order.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductUpdateRequestDTO {

    @NonNull
    private UUID productId;
    @NonNull
    private String productCode;
    private String productName;
    private String productDesc;
    private long stockQty;
    private double price;

}