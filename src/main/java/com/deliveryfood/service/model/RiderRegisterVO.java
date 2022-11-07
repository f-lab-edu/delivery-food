package com.deliveryfood.service.model;

import com.deliveryfood.controller.model.request.RiderRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class RiderRegisterVO extends MemberRegisterVO {

    private int commission;

    public static RiderRegisterVO convert(RiderRegisterRequest registerRequest) {
        return RiderRegisterVO.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .phone(registerRequest.getPhone())
                .commission(registerRequest.getCommission())
                .build();
    }
}
