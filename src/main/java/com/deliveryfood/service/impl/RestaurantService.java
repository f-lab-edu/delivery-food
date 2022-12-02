package com.deliveryfood.service.impl;

import com.deliveryfood.dao.RestaurantDao;
import com.deliveryfood.dto.RestaurantDto;
import com.deliveryfood.service.IRestaurantService;
import com.deliveryfood.service.model.RestaurantRegisterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RestaurantService implements IRestaurantService {

    private final RestaurantDao restaurantDao;

    @Override
    public boolean register(RestaurantRegisterVO restaurantRegisterVO) {
        String restaurantId = ObjectUtils.isEmpty(restaurantRegisterVO.getRestaurantId()) ? UUID.randomUUID().toString() : restaurantRegisterVO.getRestaurantId();
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .restaurantId(restaurantId)
                .userId(restaurantRegisterVO.getUserId())
                .name(restaurantRegisterVO.getName())
                .build();
        restaurantDao.register(restaurantDto);
        return true;
    }

    @Override
    public void deleteByRestaurantId(String restaurantId) {
        restaurantDao.deleteByRestaurantId(restaurantId);
    }

    @Override
    public RestaurantRegisterVO findUserById(RestaurantRegisterVO restaurantRegisterVO) {
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .restaurantId(restaurantRegisterVO.getRestaurantId())
                .userId(restaurantRegisterVO.getUserId())
                .build();
        RestaurantDto restaurantOutput = restaurantDao.findUserById(restaurantDto);

        if(ObjectUtils.isEmpty(restaurantOutput)) {
            throw new NullPointerException("가게가 존재하지 않음 : " + restaurantDto.getRestaurantId());
        }

        return RestaurantRegisterVO.builder()
                .restaurantId(restaurantOutput.getRestaurantId())
                .userId(restaurantOutput.getUserId())
                .state(restaurantOutput.getState())
                .build();
    }


}