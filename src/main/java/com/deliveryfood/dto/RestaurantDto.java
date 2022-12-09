package com.deliveryfood.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class RestaurantDto {
    private String restaurantId;
    private String userId;
    private String name;
    private String state;
}