package com.deliveryfood.service;

import com.deliveryfood.dao.UserDao;
import com.deliveryfood.dto.UserDto;
import com.deliveryfood.model.UserInput;
import com.deliveryfood.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public static final String REGISTER_CODE = "FLAB";
    private final UserDao userDao;

    @Override
    public boolean certification(UserRequest userRequest, String code) {
        // REGISTER_CODE 와 일치하면 인증 완료

        UserDto userDto = userDao.findById(userRequest.getEmail());
        if(userDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        if(!code.equals(REGISTER_CODE)) {
            // 인증 코드가 다름
            return false;
        }

        userDto.setStatus(UserDto.Status.REGISTER);
        userDao.updateStatus(userDto);
        return true;
    }

    @Override
    public boolean register(UserInput userInput) {
        // 유저 중복 체크 추가
        // 비밀번호 암호화 추가

        UserDto userDto = UserDto.builder()
                .userId(UUID.randomUUID().toString())
                .name(userInput.getName())
                .email(userInput.getEmail())
                .password(userInput.getPassword())
                .phone(userInput.getPhone())
                .address(userInput.getAddress())
                .status(UserDto.Status.REGISTER_AUTH)
                .regDt(LocalDateTime.now())
                .build();

        userDao.register(userDto);
        return true;
    }

    @Override
    public boolean withdraw(UserRequest userRequest) {
        UserDto userDto = userDao.findById(userRequest.getEmail());
        if(userDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        if(!userDto.getPassword().equals(userRequest.getPassword())) {
            // 비밀번호가 다름
            return false;
        }

        userDto.setStatus(UserDto.Status.WITHDRAW);
        userDao.updateStatus(userDto);
        return true;
    }

    @Override
    public boolean login(UserRequest userRequest) {
        UserDto userDto = userDao.findById(userRequest.getEmail());
        if(userDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        if(userDto.getStatus().equals(UserDto.Status.REGISTER_AUTH)) {
            // 본인 인증 완료 전
            return false;
        }

        if(!userDto.getPassword().equals(userRequest.getPassword())) {
            // 비밀번호가 다름
            return false;
        }
        return true;
    }


}