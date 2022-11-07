package com.deliveryfood.service.model;


import com.deliveryfood.controller.model.request.UserRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class UserRegisterVO extends MemberRegisterVO {

    private String address;
    private String nickname;
    private String imagePath;

    public static UserRegisterVO convert(UserRegisterRequest registerRequest) {
        return UserRegisterVO.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .nickname(registerRequest.getNickname())
                .imagePath(registerRequest.getImagePath())
                .build();
    }
}
