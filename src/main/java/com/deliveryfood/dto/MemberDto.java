package com.deliveryfood.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    public enum Status {
        NONE,
        REGISTER_AUTH,
        REGISTER,
        WITHDRAW;
    }

    @NonNull
    private String userId;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String phone;
    @NonNull
    private Status status;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;

}
