package com.deliveryfood.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CartMenuRequest {

    private int idx;
    private int menuId;
    private int count;
}
