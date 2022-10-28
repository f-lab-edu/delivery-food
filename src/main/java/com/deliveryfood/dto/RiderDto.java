package com.deliveryfood.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDto {

    public enum Status {
        NONE,
        ENABLE_MATCHING,
        ON_DELIVERY,
        COMPLETE_DELIVERY,
    }

    @NonNull
    private String userId;
    @NonNull
    private int commission;
    @NonNull
    private Status status;
    @NonNull
    private LocalDateTime regDt;
    @NonNull
    private LocalDateTime udtDt;

}
