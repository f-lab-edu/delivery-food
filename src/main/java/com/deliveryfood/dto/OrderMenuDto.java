package com.deliveryfood.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OrderMenuDto {
    private String orderId;
    private String userId;
    private String state;
    //TODO : 나머지 필드도 추가 예정
}