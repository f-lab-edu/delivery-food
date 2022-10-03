package com.deliveryfood.controller;

import com.deliveryfood.dto.UserDto;
import com.deliveryfood.model.UserInput;
import com.deliveryfood.model.UserRequest;
import com.deliveryfood.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @PostMapping("/certification")
    public void certification(@RequestBody UserRequest userRequest, @RequestParam String code)  {
        // 입력한 코드로 본인 인증
        userService.certification(userRequest, code);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserInput userInput) {
        // 회원 가입 > 본인 인증 요청
        userService.register(userInput);
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestBody UserRequest userRequest) {
        // 회원 탈퇴 (session을 삭제할 뿐 정보의 변경은 없다.)
        userService.withdraw(userRequest);
    }

    @PostMapping("/login")
    public void login(UserRequest userRequest) {
        // 로그인
        userService.login(userRequest);
    }

    @PostMapping("/logout")
    public void logout() {
        // 로그아웃
    }

    @GetMapping("/{email}")
    public void findUser(@PathVariable String email) {
        // 회원 조회
        UserDto userDto = userService.findUser(email);
    }

    @PutMapping("/{userId}")
    public void modifyUser(UserInput userInput) {
        // 회원 정보 수정 (현재는 전화번호, 주소만 수정가능)
        userService.modifyUser(userInput);
   }

    @GetMapping("/{userId}/orders")
    public void findOrderByUserId(@PathVariable String userId) {
        // 유저의 모든 주문을 조회
    }
}