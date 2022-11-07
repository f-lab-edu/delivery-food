package com.deliveryfood.controller;

import com.deliveryfood.common.mock.auth.WithAuthMember;
import com.deliveryfood.controller.model.request.RiderRegisterRequest;
import com.deliveryfood.controller.model.request.RiderUpdateRequest;
import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.service.impl.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class RiderControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private RiderController riderController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(riderController)
                .defaultRequest(get("/").with(testSecurityContext()))
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    @DisplayName("라이더 회원가입시 본인 인증을 처리한다.")
    @WithAuthMember(username = "rider@gmail.com", authority = "ROLE_RIDER,ROLE_NOT_AUTH")
    public void testCertification() throws Exception {
        mockMvc.perform(post("/riders/certification")
                .characterEncoding("utf-8")
                .param("code", MemberService.REGISTER_CODE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("라이더 회원 가입을 한다")
    public void testRegister() throws Exception {
        RiderRegisterRequest registerRequest = RiderRegisterRequest.builder()
                .name("라이더")
                .email("rider@gmail.com")
                .password("riderpassword")
                .phone("010-1234-5678")
                .commission(3000)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/riders/register")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("라이더 회원 탈퇴를 한다.")
    @WithAuthMember(username = "rider@gmail.com", authority = "ROLE_RIDER,ROLE_AUTH")
    public void testWithdraw() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("rider@gmail.com")
                .password("riderpassword")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/riders/withdraw")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("라이더 회원 로그인을 한다.")
    @WithAuthMember(username = "rider@gmail.com", authority = "ROLE_RIDER,ROLE_AUTH")
    public void testLogin() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("rider@gmail.com")
                .password("riderpassword")
                .build();

        mockMvc.perform(formLogin()
                .user(userRequest.getEmail())
                .password(userRequest.getPassword()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("라이더 회원 로그아웃을 한다.")
    @WithAuthMember(username = "rider@gmail.com", authority = "ROLE_RIDER,ROLE_AUTH")
    public void testLogout() throws Exception {
        mockMvc.perform(logout())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("userId 로부터 라이더 회원 정보를 수정한다.")
    @WithAuthMember(username = "rider@gmail.com", authority = "ROLE_RIDER,ROLE_AUTH")
    public void testModifyUser() throws Exception {
        RiderUpdateRequest updateRequest = RiderUpdateRequest.builder()
                .commission(5000)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/riders/modifyRider")
                    .characterEncoding("utf-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
