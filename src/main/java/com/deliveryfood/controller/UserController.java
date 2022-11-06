package com.deliveryfood.controller;

import com.deliveryfood.controller.model.request.UserRegisterRequest;
import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.controller.model.request.UserUpdateRequest;
import com.deliveryfood.service.IUserService;
import com.deliveryfood.util.SecurityPrincipal;
import com.deliveryfood.service.model.UserRegisterVO;
import com.deliveryfood.service.model.UserUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @PostMapping("/certification")
    public void certification(@RequestParam String code)  {
        // 입력한 코드로 본인 인증
        String userId = SecurityPrincipal.getLoginUserId();
        userService.certification(userId, code);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserRegisterRequest registerRequest) {
        // 회원 가입 > 본인 인증 요청
        UserRegisterVO registerVO = UserRegisterVO.convert(registerRequest);
        userService.register(registerVO);
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestBody UserRequest userRequest) {
        // 회원 탈퇴 (session을 삭제할 뿐 정보의 변경은 없다.)
        String userId = SecurityPrincipal.getLoginUserId();
        userService.withdraw(userId, userRequest);
    }

    @PutMapping("/modifyUser")
    public void modifyUser(@RequestBody UserUpdateRequest updateRequest) {
        // 회원 정보 수정 (주소, 닉네임, 프로필사진만 수정 가능)
        String userId = SecurityPrincipal.getLoginUserId();
        UserUpdateVO updateVO = UserUpdateVO.convert(updateRequest);
        userService.modifyUser(userId, updateVO);
   }
}