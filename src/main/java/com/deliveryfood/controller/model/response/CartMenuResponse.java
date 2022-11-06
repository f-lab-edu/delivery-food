package com.deliveryfood.controller.model.response;

import com.deliveryfood.dto.CartMenuDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CartMenuResponse {

    private int index;
    private int menuId;
    private int count;

    public static CartMenuResponse convert (CartMenuDto menuDto) {
        return CartMenuResponse.builder()
                .index(menuDto.getIdx())
                .menuId(menuDto.getMenuId())
                .count(menuDto.getCount())
                .build();
    }
}
