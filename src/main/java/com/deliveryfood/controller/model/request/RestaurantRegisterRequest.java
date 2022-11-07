package com.deliveryfood.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RestaurantRegisterRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private String restaurantId;
    private String userId;
    private String state;
}
