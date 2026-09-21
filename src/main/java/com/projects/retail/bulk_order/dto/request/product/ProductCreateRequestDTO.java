package com.projects.retail.bulk_order.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ProductCreateRequestDTO {

    @NonNull
    private String productCode;
    @NonNull
    private String productName;
    private String productDesc;
    @NonNull
    private long stockQty;
    @NonNull
    private double price;

}