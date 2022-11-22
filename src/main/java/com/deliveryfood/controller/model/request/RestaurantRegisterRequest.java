package com.deliveryfood.controller.model.request;

import com.deliveryfood.dto.RestaurantDto.FoodCategory;
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
    private Integer businessId;
    private FoodCategory foodCategory;
    private String address;
    private String phone;
    private String openingHours;
    private Integer distance;
}
