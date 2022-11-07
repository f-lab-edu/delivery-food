package com.deliveryfood.service.model;

import com.deliveryfood.controller.model.request.RestaurantUserRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
public class RestaurantUserRegisterVO extends MemberRegisterVO {

    public static RestaurantUserRegisterVO convert(RestaurantUserRegisterRequest registerRequest) {
        return RestaurantUserRegisterVO.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .phone(registerRequest.getPhone())
                .build();
    }

}
