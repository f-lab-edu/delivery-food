package com.deliveryfood.service;

import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.service.model.RiderRegisterVO;
import com.deliveryfood.service.model.RiderUpdateVO;

public interface IRiderService {

    boolean certification(String userId, String code);

    boolean register(RiderRegisterVO registerVO);

    boolean withdraw(String userId, UserRequest userRequest);

    boolean modifyRider(String userId, RiderUpdateVO registerVO);

    void deleteRiderByEmail(String email);
}
