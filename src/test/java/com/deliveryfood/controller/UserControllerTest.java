package com.deliveryfood.controller;

import com.deliveryfood.common.mock.auth.WithAuthMember;
import com.deliveryfood.controller.model.request.UserRegisterRequest;
import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.controller.model.request.UserUpdateRequest;
import com.deliveryfood.service.impl.MemberService;
import com.deliveryfood.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .defaultRequest(get("/").with(testSecurityContext()))
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @AfterEach
    public void clear() {
        userService.deleteUserByEmail("test@gmail.com");
    }

    @Test
    @DisplayName("??????????????? ?????? ????????? ????????????.")
    @WithAuthMember(username = "test@gmail.com", password = "test1234", authority = "ROLE_USER,ROLE_NOT_AUTH")
    public void testCertification() throws Exception {
        mockMvc.perform(post("/users/certification")
                .characterEncoding("utf-8")
                .param("code", MemberService.REGISTER_CODE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ????????? ??????")
    public void testRegister() throws Exception {
        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                .name("?????????")
                .email("test@gmail.com")
                .password("test1234")
                .phone("010-1234-5678")
                .address("????????? ????????? ????????????")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/users/register")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithAuthMember(username = "test@gmail.com", password = "test1234", authority = "ROLE_USER,ROLE_AUTH")
    @DisplayName("?????? ????????? ??????.")
    public void testWithdraw() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("test@gmail.com")
                .password("test1234")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/users/withdraw")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ???????????? ??????.")
    @WithAuthMember(username = "test@gmail.com", password = "test1234", authority = "ROLE_USER,ROLE_AUTH")
    public void testLogin() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("test@gmail.com")
                .password("test1234")
                .build();

        mockMvc.perform(formLogin()
                .user(userRequest.getEmail())
                .password(userRequest.getPassword()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ??????????????? ??????.")
    @WithAuthMember(username = "test@gmail.com", password = "test1234", authority = "ROLE_USER,ROLE_AUTH")
    public void testLogout() throws Exception {
        mockMvc.perform(logout())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("userId ????????? ?????? ????????? ????????????.")
    @WithAuthMember(username = "test@gmail.com", password = "test1234", authority = "ROLE_USER,ROLE_AUTH")
    public void testModifyUser() throws Exception {
        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .address("????????? ????????? ?????????")
                .nickname("???????????????")
                .imagePath("image")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/users/modifyUser")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}