package com.deliveryfood.model;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
}