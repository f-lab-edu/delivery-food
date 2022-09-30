package com.deliveryfood.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserInput {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String nickname;
    private String imagePath;
}
