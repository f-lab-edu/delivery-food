package com.deliveryfood.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(force=true)
@AllArgsConstructor
@Builder
@Value
public class RestaurantMenuOptionRequest {
    private String restaurantId;
    private String menuId;
    private String optionId;
    private String subOptionId;
    private String name;
    private String price;
    //TODO : 나머지 필드도 추가 예정
}