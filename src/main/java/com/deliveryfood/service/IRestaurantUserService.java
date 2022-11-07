package com.deliveryfood.service;

import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.service.model.RestaurantUserRegisterVO;

public interface IRestaurantUserService {

    boolean certification(String username, String code);

    boolean register(RestaurantUserRegisterVO registerVO);

    boolean withdraw(UserRequest userRequest);
}
