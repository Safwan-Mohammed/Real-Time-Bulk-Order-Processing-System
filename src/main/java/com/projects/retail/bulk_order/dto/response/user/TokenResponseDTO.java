package com.projects.retail.bulk_order.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private final String tokenType = "Bearer";
}
