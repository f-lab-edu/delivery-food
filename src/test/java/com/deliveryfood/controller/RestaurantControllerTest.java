package com.deliveryfood.controller;

import com.deliveryfood.controller.model.request.MenuRequest;
import com.deliveryfood.controller.model.request.OptionRequest;
import com.deliveryfood.controller.model.request.SubOptionRequest;
import com.deliveryfood.controller.model.request.RestaurantRegisterRequest;
import com.deliveryfood.controller.model.request.RestaurantMenuOptionRequest;
import com.deliveryfood.service.IOptionService;
import com.deliveryfood.service.ISubOptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RestaurantControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private RestaurantController restaurantController;

    @Autowired
    IOptionService optionService;
    @Autowired
    ISubOptionService subOptionService;
    private String[] optionIdArr;
    private String[] menuIdArr;
    private String[] nameArr;
    private String[] subOptionIdArr;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();

        createDummyData();
    }

    @AfterEach
    public void tearDown() {
        deleteDummyData();
    }

    private void createDummyData() {
        //create Option Dummy Data
        optionService.createOption(OptionRequest.builder().optionId("1001").menuId("2001").name("test name 1").build());
        optionService.createOption(OptionRequest.builder().optionId("1002").menuId("2001").name("test name 2").build());
        optionService.createOption(OptionRequest.builder().optionId("1003").menuId("2002").name("test name 3").build());
        optionService.createOption(OptionRequest.builder().optionId("1004").menuId("2001").name("test name 4").build());

        //create SubOption Dummy Data;
        subOptionService.createSubOption(SubOptionRequest.builder().optionId("1001").menuId("2001").subOptionId("3001").build());
        subOptionService.createSubOption(SubOptionRequest.builder().optionId("1001").menuId("2001").subOptionId("3002").build());
        subOptionService.createSubOption(SubOptionRequest.builder().optionId("1002").menuId("2001").subOptionId("3003").build());
        subOptionService.createSubOption(SubOptionRequest.builder().optionId("1003").menuId("2002").subOptionId("3004").build());
    }

    private void deleteDummyData() {
        //delete Option Dummy Data
        optionService.deleteOptionById(OptionRequest.builder().optionId("1001").build());
        optionService.deleteOptionById(OptionRequest.builder().optionId("1002").build());
        optionService.deleteOptionById(OptionRequest.builder().optionId("1003").build());
        optionService.deleteOptionById(OptionRequest.builder().optionId("1004").build());

        //delete SubOption Dummy Data
        subOptionService.deleteSubOptionById(SubOptionRequest.builder().subOptionId("3001").build());
        subOptionService.deleteSubOptionById(SubOptionRequest.builder().subOptionId("3002").build());
        subOptionService.deleteSubOptionById(SubOptionRequest.builder().subOptionId("3003").build());
        subOptionService.deleteSubOptionById(SubOptionRequest.builder().subOptionId("3004").build());
    }


    @Test
    @DisplayName("가게 회원가입시 본인 인증을 처리한다.")
    public void testCertification() throws Exception {
        mockMvc.perform(post("/restaurants/certification")
                .param("code", String.valueOf(ArgumentMatchers.anyInt())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("가게 회원 가입을 한다")
    public void testRegister() throws Exception {
        RestaurantRegisterRequest registerRequest = RestaurantRegisterRequest.builder()
                .name("테스트")
                .email("test@gmail.com")
                .password("testpassword")
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
    @DisplayName("가게 회원 탈퇴를 한다.")
    public void testWithdraw() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("userId", String.valueOf(ArgumentMatchers.anyInt()));
        map.set("password", "testpassword");

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/restaurants/withdraw")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("가게 회원 로그인을 한다.")
    public void testLogin() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("userId", String.valueOf(ArgumentMatchers.anyInt()));
        map.set("password", "testpassword");

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/restaurants/login")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("가게 회원 로그아웃을 한다.")
    public void testLogout() throws Exception {
        mockMvc.perform(post("/restaurants/logout"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("userId로부터 가게 회원을 조회한다.")
    public void testFindUser() throws Exception {
        int userId = ArgumentMatchers.anyInt();
        mockMvc.perform(get("/restaurants/" + userId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("userId 로부터 가게 회원 정보를 수정한다.")
    public void testModifyUser() throws Exception {
        int userId = ArgumentMatchers.anyInt();
        mockMvc.perform(put("/restaurants/" + userId))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void findUsers() throws Exception {
        //then
        mockMvc.perform(get("/restaurants/"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findUserById() throws Exception {
        //given and when
        RestaurantRegisterRequest registerRequest = RestaurantRegisterRequest.builder()
                .restaurantId("9419ab0c-9353-491a-aaee-fe0b8d175d5d")
                .build();

        //then
        mockMvc.perform(get("/restaurants/" + registerRequest.getRestaurantId())
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void modifyUserById() throws Exception {
        //given and when
        RestaurantRegisterRequest registerRequest = RestaurantRegisterRequest.builder()
                .restaurantId("9419ab0c-9353-491a-aaee-fe0b8d175d5d")
                .name("name 수정 완료")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        //then
        mockMvc.perform(put("/restaurants/" + registerRequest.getRestaurantId())
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }



    @Test
    void createMenuById() throws Exception {
        //given and when
        MenuRequest menuRequest = MenuRequest.builder()
                .restaurantId("9419ab0c-9353-491a-aaee-fe0b8d175d5d")
                .name("name 테스트")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        //then
        mockMvc.perform(post("/restaurants/"
                        + menuRequest.getRestaurantId()
                        + "/menus")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void findMenus() throws Exception {
        //given and when
        MenuRequest menuRequest = MenuRequest.builder()
                .restaurantId("9419ab0c-9353-491a-aaee-fe0b8d175d5d")
                .menuId("39902574-3b9a-4f3f-9b8c-785c130dd10a")
                .build();

        //then
        mockMvc.perform(get("/restaurants/"
                        + menuRequest.getRestaurantId()
                        + "/menus/"
                        + menuRequest.getMenuId())
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findMenuById() throws Exception {
        //given and when
        MenuRequest menuRequest = MenuRequest.builder()
                .restaurantId("9419ab0c-9353-491a-aaee-fe0b8d175d5d")
                .build();

        //then
        mockMvc.perform(get("/restaurants/"
                        + menuRequest.getRestaurantId()
                        + "/menus/")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void modifyMenuById() throws Exception {
        //given and when
        MenuRequest menuRequest = MenuRequest.builder()
                .restaurantId("9419ab0c-9353-491a-aaee-fe0b8d175d5d")
                .menuId("39902574-3b9a-4f3f-9b8c-785c130dd10a")
                .name("name 수정 완료")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        //then
        mockMvc.perform(put("/restaurants/"
                        + menuRequest.getRestaurantId()
                        + "/menus/"
                        + menuRequest.getMenuId())
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void findSubOptions() throws Exception {
        //given and when
        RestaurantMenuOptionRequest restaurantMenuOptionRequest = RestaurantMenuOptionRequest.builder()
                .restaurantId("9419ab0c-9353-491a-aaee-fe0b8d175d5d")
                .menuId("2001")
                .build();

        //then
        mockMvc.perform(get("/restaurants/"
                        + restaurantMenuOptionRequest.getRestaurantId()
                        + "/menus/"
                        + restaurantMenuOptionRequest.getMenuId())
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
