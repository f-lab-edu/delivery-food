package com.deliveryfood.service.model;

import com.deliveryfood.controller.model.request.RestaurantRegisterRequest;
import com.deliveryfood.dto.RestaurantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class RestaurantRegisterVO extends MemberRegisterVO {
    private String name;
    private Integer businessId;
    private RestaurantDto.FoodCategory foodCategory;
    private String address;
    private String phone;
    private String openingHours;
    private Integer distance;

    public static RestaurantRegisterVO convert(RestaurantRegisterRequest registerRequest) {
        return RestaurantRegisterVO.builder()
                .name(registerRequest.getName())
                .businessId(registerRequest.getBusinessId())
                .foodCategory(registerRequest.getFoodCategory())
                .address(registerRequest.getAddress())
                .phone(registerRequest.getPhone())
                .openingHours(registerRequest.getOpeningHours())
                .distance(registerRequest.getDistance())
                .build();
    }
}
