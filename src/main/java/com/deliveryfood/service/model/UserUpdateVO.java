package com.deliveryfood.service.model;

import com.deliveryfood.controller.model.request.UserUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserUpdateVO {

    private String address;
    private String nickname;
    private String imagePath;

    public static UserUpdateVO convert(UserUpdateRequest updateRequest) {
        return UserUpdateVO.builder()
                .address(updateRequest.getAddress())
                .nickname(updateRequest.getNickname())
                .imagePath(updateRequest.getImagePath())
                .build();
    }
}
