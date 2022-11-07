package com.deliveryfood.dto;

import com.deliveryfood.service.model.CartOptionVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartOptionDto {

    @NonNull
    private int optionId;
    @NonNull
    private int subOptionId;
    @NonNull
    private int price;

    public static List<CartOptionDto> convert(List<CartOptionVO> optionVOList) {
        List<CartOptionDto> optionDtoList = new ArrayList<>();
        for(CartOptionVO option : optionVOList) {
            optionDtoList.add(convert(option));
        }
        return optionDtoList;
    }

    public static CartOptionDto convert(CartOptionVO optionVO) {
        return CartOptionDto.builder()
                .optionId(optionVO.getOptionId())
                .subOptionId(optionVO.getSubOptionId())
                .price(optionVO.getPrice())
                .build();
    }
}
