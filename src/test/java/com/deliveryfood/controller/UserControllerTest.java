package com.deliveryfood.controller;

import com.deliveryfood.common.mock.auth.WithAuthMember;
import com.deliveryfood.service.MemberService;
import com.deliveryfood.dao.MemberDao;
import com.deliveryfood.dao.UserDao;
import com.deliveryfood.model.request.UserRegisterRequest;
import com.deliveryfood.model.request.UserRequest;
import com.deliveryfood.model.request.UserUpdateRequest;
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
    private UserDao userDao;

    @Autowired
    private MemberDao memberDao;

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
        memberDao.deleteAllMember();
        userDao.deleteAllUser();
    }

    @Test
    @DisplayName("회원가입시 본인 인증을 처리한다.")
    @WithAuthMember(username = "test@gmail.com", password = "test1234", authority = "ROLE_USER,ROLE_NOT_AUTH")
    public void testCertification() throws Exception {
        mockMvc.perform(post("/users/certification")
                .characterEncoding("utf-8")
                .param("code", MemberService.REGISTER_CODE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 가입을 한다")
    public void testRegister() throws Exception {
        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                .name("테스트123")
                .email("test123@gmail.com")
                .password("testpassword")
                .phone("010-1234-5678")
                .address("서울시 구로구 디지털로")
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
    @DisplayName("회원 탈퇴를 한다.")
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
    @DisplayName("회원 로그인을 한다.")
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
    @DisplayName("회원 로그아웃을 한다.")
    @WithAuthMember(username = "test@gmail.com", password = "test1234", authority = "ROLE_USER,ROLE_AUTH")
    public void testLogout() throws Exception {
        mockMvc.perform(logout())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("userId 로부터 회원 정보를 수정한다.")
    @WithAuthMember(username = "test@gmail.com", password = "test1234", authority = "ROLE_USER,ROLE_AUTH")
    public void testModifyUser() throws Exception {
        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .address("서울시 강남구 학동로")
                .nickname("유저닉네임")
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