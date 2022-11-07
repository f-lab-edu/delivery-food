package com.deliveryfood.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(force=true)
@AllArgsConstructor
@Builder
@Value
public class OrderMenuRequest {
    private String menuId;
    private String optionId;
    private String subOptionId;
    private String price;
    //TODO : 나머지 필드도 추가 예정
}
