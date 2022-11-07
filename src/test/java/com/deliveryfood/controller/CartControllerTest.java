package com.deliveryfood.controller;

import com.deliveryfood.common.mock.auth.WithAuthMember;
import com.deliveryfood.controller.model.request.CartMenuRequest;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CartControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private CartController cartController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(cartController)
                .defaultRequest(get("/").with(testSecurityContext()))
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    @DisplayName("userId 로부터 장바구니를 조회한다.")
    @WithAuthMember(username = "test@gmail.com", authority = "ROLE_USER,ROLE_AUTH")
    public void testFindCart() throws Exception {
        mockMvc.perform(get("/carts"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("userId 로부터 장바구니를 삭제한다.")
    @WithAuthMember(username = "test@gmail.com", authority = "ROLE_USER,ROLE_AUTH")
    public void testDeleteCart() throws Exception {
        mockMvc.perform(delete("/carts"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("menuId를 사용해서 장바구니에 메뉴를 추가한다.")
    @WithAuthMember(username = "test@gmail.com", authority = "ROLE_USER,ROLE_AUTH")
    public void testAddMenu() throws Exception {
        CartMenuRequest menuRequest = CartMenuRequest.builder()
                .index(1)
                .menuId(1001)
                .count(1)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/carts/add")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("index로부터 장바구니에 매뉴를 삭제한다.")
    @WithAuthMember(username = "test@gmail.com", authority = "ROLE_USER,ROLE_AUTH")
    public void testDeleteMenu() throws Exception {
        int index = 1;
        mockMvc.perform(post("/carts/sub/" + index))
                .andExpect(status().isOk());
    }
}