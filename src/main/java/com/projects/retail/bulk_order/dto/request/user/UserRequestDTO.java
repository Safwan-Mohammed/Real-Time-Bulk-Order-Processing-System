package com.projects.retail.bulk_order.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NonNull
    private String email;

    @NonNull
    private String password;
    private String name;

}