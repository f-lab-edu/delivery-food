package com.deliveryfood.vo;

import com.deliveryfood.model.request.RiderUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RiderUpdateVO {

    private int commission;

    public static RiderUpdateVO convert(RiderUpdateRequest updateRequest) {
        return RiderUpdateVO.builder()
                .commission(updateRequest.getCommission())
                .build();
    }
}
