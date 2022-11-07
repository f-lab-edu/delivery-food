package com.deliveryfood.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RiderRegisterRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private int commission;
}
