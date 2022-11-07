package com.deliveryfood.service.impl;

import com.deliveryfood.dto.RestaurantDto;
import com.deliveryfood.mapper.RestaurantMapper;
import com.deliveryfood.service.IRestaurantService;
import com.deliveryfood.service.model.RestaurantRegisterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService implements IRestaurantService {

    private final RestaurantMapper restaurantMapper;

    @Override
    public void signin(RestaurantRegisterVO restaurantInput) {
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .restaurantId(restaurantInput.getRestaurantId())
                .userId(restaurantInput.getUserId())
                .name(restaurantInput.getName())
                .build();
        restaurantMapper.signin(restaurantDto);
    }

    @Override
    public void signout(RestaurantRegisterVO restaurantInput) {
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .restaurantId(restaurantInput.getRestaurantId())
                .build();
        restaurantMapper.signout(restaurantDto);
    }

    @Override
    public List<RestaurantDto> findUsers() {
        return restaurantMapper.findUsers();
    }

    @Override
    public RestaurantDto findUserById(RestaurantRegisterVO registerVO) {
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .restaurantId(registerVO.getRestaurantId())
                .userId(registerVO.getUserId())
                .build();
        return restaurantMapper.findUserById(restaurantDto);
    }

    @Override
    public void modifyUserById(RestaurantRegisterVO registerVO) {
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .restaurantId(registerVO.getRestaurantId())
                .name(registerVO.getName())
                .build();
        restaurantMapper.modifyUserById(restaurantDto);
    }


}