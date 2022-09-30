package com.deliveryfood.service;

import com.deliveryfood.dto.UserDto;
import com.deliveryfood.model.UserInput;
import com.deliveryfood.model.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    boolean certification(UserRequest userRequest, String code);

    boolean register(UserInput userInput);

    boolean withdraw(UserRequest userRequest);

    boolean login(UserRequest userRequest);

    boolean modifyUser(UserInput userInput);

    UserDto findUser(String email);
}
