package com.deliveryfood.service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@RequiredArgsConstructor
@SuperBuilder
@Getter
public class MemberRegisterVO {

    public String name;
    public String email;
    public String password;
    public String phone;
}
