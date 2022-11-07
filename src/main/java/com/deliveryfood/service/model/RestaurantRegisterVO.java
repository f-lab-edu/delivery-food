package com.deliveryfood.service.model;

import com.deliveryfood.controller.model.request.RestaurantRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class RestaurantRegisterVO extends MemberRegisterVO {
    private String restaurantId;
    private String userId;
    private String state;
    //TODO : 나머지 필드도 추가 예정

    public static RestaurantRegisterVO convert(RestaurantRegisterRequest registerRequest) {
        return RestaurantRegisterVO.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .phone(registerRequest.getPhone())
                .restaurantId(registerRequest.getRestaurantId())
                .userId(registerRequest.getUserId())
                .state(registerRequest.getState())
                .build();
    }
}
