package com.deliveryfood.service;

import com.deliveryfood.dto.MemberDto;
import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.service.model.MemberRegisterVO;
import com.deliveryfood.service.model.UserRegisterVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IMemberService extends UserDetailsService {

    boolean certification(String userId, String code);

    boolean register(MemberRegisterVO registerVO, String uuid, MemberDto.Role role);

    boolean withdraw(UserRequest userRequest);

    MemberDto findMemberByEmail(String email);

    String getUserId(String email);

    boolean modifyUser(UserRegisterVO registerVO);

    void deleteMemberByUserId(String email);
}
