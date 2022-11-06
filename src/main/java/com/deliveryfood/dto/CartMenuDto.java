package com.deliveryfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartMenuDto {

    @NonNull
    private String userId;
    @NonNull
    private int idx;
    @NonNull
    private int menuId;
    @NonNull
    private int count;
    @NonNull
    private LocalDateTime regDt;
    private LocalDateTime udtDt;
}
