package com.deliveryfood.controller;

import com.deliveryfood.controller.model.request.RestaurantUserRegisterRequest;
import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.service.IRestaurantUserService;
import com.deliveryfood.service.model.RestaurantUserRegisterVO;
import com.deliveryfood.util.SecurityPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurants/user")
public class RestaurantUserController {

    private final IRestaurantUserService restaurantUserService;

    @PostMapping("/certification")
    public void certification(@RequestParam String code) {
        // 입력한 코드로 본인 인증
        String userId = SecurityPrincipal.getLoginUserId();
        restaurantUserService.certification(userId, code);
    }

    @PostMapping("/register")
    public void register(@RequestBody RestaurantUserRegisterRequest registerRequest) {
        // 레스토랑 회원 가입 > 본인 인증 요청
        RestaurantUserRegisterVO registerVO = RestaurantUserRegisterVO.convert(registerRequest);
        restaurantUserService.register(registerVO);
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestBody UserRequest userRequest) {
        // 레스토랑 회원 탈퇴 (session을 삭제할 뿐 정보의 변경은 없다.)
        String userId = SecurityPrincipal.getLoginUserId();
        restaurantUserService.withdraw(userId, userRequest);
    }
}
