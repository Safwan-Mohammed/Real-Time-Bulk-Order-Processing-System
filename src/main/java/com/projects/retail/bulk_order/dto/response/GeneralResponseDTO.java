package com.projects.retail.bulk_order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class GeneralResponseDTO<T> {

    @NonNull
    private String message;

    private T data;
}