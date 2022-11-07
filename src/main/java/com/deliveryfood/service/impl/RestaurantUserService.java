package com.deliveryfood.service.impl;

import com.deliveryfood.dao.RestaurantUserDao;
import com.deliveryfood.dto.MemberDto;
import com.deliveryfood.dto.RestaurantUserDto;
import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.service.IMemberService;
import com.deliveryfood.service.IRestaurantUserService;
import com.deliveryfood.service.model.RestaurantUserRegisterVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RestaurantUserService implements IRestaurantUserService {

    private final IMemberService memberService;
    private final RestaurantUserDao restaurantUserDao;

    public RestaurantUserService(IMemberService memberService, RestaurantUserDao restaurantUserDao) {
        this.memberService = memberService;
        this.restaurantUserDao = restaurantUserDao;
    }

    @Override
    public boolean certification(String username, String code) {
        // REGISTER_CODE 와 일치하면 인증 완료

        if(!memberService.certification(username, code)) {
            // 멤버 이슈가 있음
            return false;
        }

        RestaurantUserDto restaurantUserDto = restaurantUserDao.findByUserId(username);
        if(restaurantUserDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        return true;
    }

    @Override
    public boolean register(RestaurantUserRegisterVO registerVO) {
        String uuid = UUID.randomUUID().toString();
        if(!memberService.register(registerVO, uuid, MemberDto.Role.ROLE_RESTAURANT)) {
            // 멤버 이슈가 있음
            return false;
        }

        if(restaurantUserDao.findByUserId(uuid) != null) {
            // 중복 유저 존재
            return false;
        }

        RestaurantUserDto restaurantUserDto = RestaurantUserDto.builder()
                .userId(uuid)
                .regDt(LocalDateTime.now())
                .udtDt(LocalDateTime.now())
                .build();

        restaurantUserDao.register(restaurantUserDto);
        return true;
    }

    @Override
    public boolean withdraw(UserRequest userRequest) {
        if(!memberService.withdraw(userRequest)) {
            // 멤버 이슈가 있음
            return false;
        }

        RestaurantUserDto restaurantUserDto = restaurantUserDao.findByEmail(userRequest.getEmail());
        if(restaurantUserDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        return true;
    }
}
