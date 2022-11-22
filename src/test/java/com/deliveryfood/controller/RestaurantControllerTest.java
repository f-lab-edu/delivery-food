package com.deliveryfood.controller;

import com.deliveryfood.controller.model.request.MenuRequest;
import com.deliveryfood.controller.model.request.OptionRequest;
import com.deliveryfood.controller.model.request.RestaurantMenuOptionRequest;
import com.deliveryfood.controller.model.request.RestaurantRegisterRequest;
import com.deliveryfood.controller.model.request.SubOptionRequest;
import com.deliveryfood.service.IOptionService;
import com.deliveryfood.service.ISubOptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .menuId("22ef7e6c-c5c2-4b24-ba2c-d7fd2bb615f2")
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
