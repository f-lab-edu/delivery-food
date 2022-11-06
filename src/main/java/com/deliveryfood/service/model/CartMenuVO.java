package com.deliveryfood.service.model;

import com.deliveryfood.controller.model.request.CartMenuRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CartMenuVO {

    private String userId;
    private int idx;
    private int menuId;
    private int count;

    public static CartMenuVO convert(String userId, CartMenuRequest menuRequest) {
        return CartMenuVO.builder()
                .userId(userId)
                .idx(menuRequest.getIdx())
                .menuId(menuRequest.getMenuId())
                .count(menuRequest.getCount())
                .build();
    }
}
