package com.deliveryfood.controller;

import com.deliveryfood.model.CustomUserDetails;
import com.deliveryfood.model.request.RestaurantUserRegisterRequest;
import com.deliveryfood.model.request.UserRequest;
import com.deliveryfood.service.IRestaurantUserService;
import com.deliveryfood.vo.RestaurantUserRegisterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public void certification(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam String code) {
        // 입력한 코드로 본인 인증
        restaurantUserService.certification(userDetails.getUsername(), code);
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
        restaurantUserService.withdraw(userRequest);
    }
}
