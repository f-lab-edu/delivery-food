package com.deliveryfood.service.impl;

import com.deliveryfood.dao.UserDao;
import com.deliveryfood.dto.MemberDto;
import com.deliveryfood.dto.UserDto;
import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.service.IMemberService;
import com.deliveryfood.service.IUserService;
import com.deliveryfood.service.model.UserRegisterVO;
import com.deliveryfood.service.model.UserUpdateVO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final IMemberService memberService;
    private final UserDao userDao;

    public UserService(IMemberService memberService, UserDao userDao) {
        this.memberService = memberService;
        this.userDao = userDao;
    }

    @Override
    public boolean certification(String userId, String code) {
        // REGISTER_CODE 와 일치하면 인증 완료
        memberService.certification(userId, code);
        UserDto userDto = userDao.findByUserId(userId);
        if(userDto == null) {
            throw new UsernameNotFoundException("User DB에 User가 존재하지 않음 : " + userId);
        }

        return true;
    }

    @Override
    public boolean register(UserRegisterVO registerVO) {
        String uuid = UUID.randomUUID().toString();
        memberService.register(registerVO, uuid, MemberDto.Role.ROLE_USER);
        if(userDao.findByUserId(uuid) != null) {
            throw new RuntimeException("User DB에 중복 유저가 존재함");
        }

        UserDto userDto = UserDto.builder()
                .userId(uuid)
                .address(registerVO.getAddress())
                .nickname(registerVO.getNickname())
                .grade(UserDto.Grade.COMMON)
                .imagePath(registerVO.getImagePath())
                .regDt(LocalDateTime.now())
                .udtDt(LocalDateTime.now())
                .build();

        userDao.register(userDto);
        return true;
    }

    @Override
    public boolean withdraw(String userId, UserRequest userRequest) {
        memberService.withdraw(userRequest);
        UserDto userDto = userDao.findByUserId(userId);
        if(userDto == null) {
            throw new UsernameNotFoundException("User DB에 User가 존재하지 않음 : " + userId);
        }

        return true;
    }

    @Override
    public boolean modifyUser(String userId, UserUpdateVO updateVO) {
        UserDto userDto = userDao.findByUserId(userId);
        if(userDto == null) {
            throw new UsernameNotFoundException("User DB에 User가 존재하지 않음 : " + userId);
        }

        userDto.setAddress(updateVO.getAddress());
        userDto.setNickname(updateVO.getNickname());
        userDto.setImagePath(updateVO.getImagePath());
        userDao.updateUser(userDto);
        return true;
    }
}
