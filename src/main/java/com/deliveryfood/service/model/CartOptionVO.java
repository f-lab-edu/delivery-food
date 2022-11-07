package com.deliveryfood.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CartOptionVO {

    private int optionId;
    private int subOptionId;
    private int price;
}
