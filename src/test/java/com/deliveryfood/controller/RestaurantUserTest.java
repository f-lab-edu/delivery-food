package com.deliveryfood.controller;

import com.deliveryfood.model.request.RestaurantUserRegisterRequest;
import com.deliveryfood.model.request.UserRequest;
import com.deliveryfood.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RestaurantUserTest {

    private MockMvc mockMvc;

    @Autowired
    private RestaurantUserController restaurantUserController;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(restaurantUserController)
                .apply(springSecurity(filterChainProxy))
                .build();
    }

    @Test
    @DisplayName("레스토랑 회원가입시 본인 인증을 처리한다.")
    @WithMockUser()
    public void testCertification() throws Exception {
        mockMvc.perform(post("/restaurants/certification")
                        .characterEncoding("utf-8")
                        .param("code", MemberService.REGISTER_CODE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("레스토랑 회원 가입을 한다")
    public void testRegister() throws Exception {
        RestaurantUserRegisterRequest registerRequest = RestaurantUserRegisterRequest.builder()
                .name("레스토랑")
                .email("restaurant@gmail.com")
                .password("restaurantpassword")
                .phone("010-1234-5678")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/restaurants/register")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("레스토랑 회원 탈퇴를 한다.")
    public void testWithdraw() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("restaurant@gmail.com")
                .password("restaurantpassword")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/restaurants/withdraw")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("레스토랑 회원 로그인을 한다.")
    @WithMockUser(roles = "RESTAURANT")
    public void testLogin() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("restaurant@gmail.com")
                .password("restaurantpassword")
                .build();

        mockMvc.perform(formLogin()
                        .user(userRequest.getEmail())
                        .password(userRequest.getPassword()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("레스토랑 회원 로그아웃을 한다.")
    public void testLogout() throws Exception {
        mockMvc.perform(post("/restaurants/logout"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
