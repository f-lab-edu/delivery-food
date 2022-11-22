package com.deliveryfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Arrays;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    public enum Status {
        NONE,
        REGISTER,
        WITHDRAW
    }

    public enum Role {
        NONE,
        ROLE_USER,
        ROLE_RESTAURANT,
        ROLE_RIDER,
        ROLE_NOT_AUTH,
        ROLE_AUTH
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
    @NonNull
    private String role;
    @NonNull
    private LocalDateTime regDt;
    @NonNull
    private LocalDateTime udtDt;

    public boolean isAuth() {
        return Arrays.stream(role.split(",")).anyMatch(e -> e.equals(Role.ROLE_AUTH.name()));
    }

    public boolean isExistRole(Role checkRole) {
        return Arrays.stream(role.split(",")).anyMatch(e -> e.equals(checkRole.name()));
    }

    public void certificateRole() {
        role = role.replace(Role.ROLE_NOT_AUTH.name(), Role.ROLE_AUTH.name());
    }

    public void registerRole(Role registerRole) {
        // 인증이 안되어 있는 멤버라면, ROLE_NOT_AUTH 추가
        // 인증이 되어있다면, registerRole만 추가
        if(!role.contains(Role.ROLE_AUTH.name()) && !role.contains(Role.ROLE_NOT_AUTH.name())) {
            if(!role.isEmpty())
                role = role.concat(",");
            role = role.concat(Role.ROLE_NOT_AUTH.name());
        }

        if(!role.contains(registerRole.name())) {
            if(!role.isEmpty())
                role = role.concat(",");
            role = role.concat(registerRole.name());
        }
    }
}
