package com.deliveryfood.controller;

import com.deliveryfood.common.mock.auth.WithAuthMember;
import com.deliveryfood.dao.MemberDao;
import com.deliveryfood.dao.RestaurantUserDao;
import com.deliveryfood.model.request.RestaurantUserRegisterRequest;
import com.deliveryfood.model.request.UserRequest;
import com.deliveryfood.service.MemberService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RestaurantUserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private RestaurantUserController restaurantUserController;

    @Autowired
    private RestaurantUserDao restaurantUserDao;

    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(restaurantUserController)
                .defaultRequest(get("/").with(testSecurityContext()))
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @AfterEach
    public void clear() {
        memberDao.deleteAllMember();
        restaurantUserDao.deleteAllRestaurantUser();
    }

    @Test
    @DisplayName("레스토랑 회원가입시 본인 인증을 처리한다.")
    @WithAuthMember(username = "restaurant@gmail.com", password = "test1234", authority = "ROLE_RESTAURANT,ROLE_NOT_AUTH")
    public void testCertification() throws Exception {
        mockMvc.perform(post("/restaurants/user/certification")
                        .characterEncoding("utf-8")
                        .param("code", MemberService.REGISTER_CODE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("레스토랑 회원 가입을 한다")
    public void testRegister() throws Exception {
        RestaurantUserRegisterRequest registerRequest = RestaurantUserRegisterRequest.builder()
                .name("레스토랑123")
                .email("restaurant123@gmail.com")
                .password("restaurantpassword")
                .phone("010-1234-5678")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/restaurants/user/register")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("레스토랑 회원 탈퇴를 한다.")
    @WithAuthMember(username = "restaurant@gmail.com", password = "test1234", authority = "ROLE_RESTAURANT,ROLE_AUTH")
    public void testWithdraw() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("restaurant@gmail.com")
                .password("test1234")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/restaurants/user/withdraw")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("레스토랑 회원 로그인을 한다.")
    @WithAuthMember(username = "restaurant@gmail.com", password = "test1234", authority = "ROLE_RESTAURANT,ROLE_AUTH")
    public void testLogin() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("restaurant@gmail.com")
                .password("test1234")
                .build();

        mockMvc.perform(formLogin()
                        .user(userRequest.getEmail())
                        .password(userRequest.getPassword()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("레스토랑 회원 로그아웃을 한다.")
    @WithAuthMember(username = "restaurant@gmail.com", password = "test1234", authority = "ROLE_RESTAURANT,ROLE_AUTH")
    public void testLogout() throws Exception {
        mockMvc.perform(logout())
                .andExpect(status().isOk())
                .andDo(print());
    }
}
