package com.deliveryfood.service;

import com.deliveryfood.dao.RestaurantUserDao;
import com.deliveryfood.dto.MemberDto;
import com.deliveryfood.dto.RestaurantUserDto;
import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.service.IMemberService;
import com.deliveryfood.service.IRestaurantUserService;
import com.deliveryfood.service.model.RestaurantUserRegisterVO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public boolean certification(String userId, String code) {
        // REGISTER_CODE 와 일치하면 인증 완료
        memberService.certification(userId, code);
        RestaurantUserDto restaurantUserDto = restaurantUserDao.findByUserId(userId);
        if(restaurantUserDto == null) {
            throw new UsernameNotFoundException("restaurantUser DB에 User가 존재하지 않음 : " + userId);
        }

        return true;
    }

    @Override
    public boolean register(RestaurantUserRegisterVO registerVO) {
        String uuid = UUID.randomUUID().toString();
        memberService.register(registerVO, uuid, MemberDto.Role.ROLE_RESTAURANT);
        if(restaurantUserDao.findByUserId(uuid) != null) {
            throw new RuntimeException("restaurantUser DB에 중복 유저가 존재함");
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
    public boolean withdraw(String userId, UserRequest userRequest) {
        memberService.withdraw(userRequest);
        RestaurantUserDto restaurantUserDto = restaurantUserDao.findByUserId(userId);
        if(restaurantUserDto == null) {
            throw new UsernameNotFoundException("restaurantUser DB에 User가 존재하지 않음 : " + userId);
        }

        return true;
    }

    @Override
    public void deleteRestaurantUserByEmail(String email) {
        String userId = memberService.getUserId(email);

        memberService.deleteMemberByUserId(userId);
        restaurantUserDao.deleteRestaurantUserByUserId(userId);
    }
}
