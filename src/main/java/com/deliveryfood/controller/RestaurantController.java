package com.deliveryfood.controller;

import com.deliveryfood.controller.model.request.MenuRequest;
import com.deliveryfood.controller.model.request.RestaurantMenuOptionRequest;
import com.deliveryfood.controller.model.request.RestaurantRegisterRequest;
import com.deliveryfood.controller.model.request.SubOptionRequest;
import com.deliveryfood.service.IMenuService;
import com.deliveryfood.service.IOptionService;
import com.deliveryfood.service.IRestaurantService;
import com.deliveryfood.service.ISubOptionService;
import com.deliveryfood.service.model.MenuRegisterVO;
import com.deliveryfood.service.model.MenuVO;
import com.deliveryfood.service.model.OptionVO;
import com.deliveryfood.service.model.RestaurantMenuOptionVO;
import com.deliveryfood.service.model.RestaurantMenuSubOptionVO;
import com.deliveryfood.service.model.RestaurantRegisterVO;
import com.deliveryfood.service.model.SubOptionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final IRestaurantService restaurantService;
    private final IMenuService menuService;
    private final IOptionService optionService;
    private final ISubOptionService subOptionService;

    @GetMapping("/{restaurantId}")
    public RestaurantRegisterRequest findUserById(@PathVariable String restaurantId
            , @RequestBody RestaurantRegisterRequest restaurantRegisterRequest) {
        // 레스토랑 회원 조회
        RestaurantRegisterVO restaurantRegisterOutput = restaurantService.findUserById(RestaurantRegisterVO.convert(restaurantRegisterRequest));

        return RestaurantRegisterRequest.builder()
                .restaurantId(restaurantRegisterOutput.getRestaurantId())
                .userId(restaurantRegisterOutput.getUserId())
                .state(restaurantRegisterOutput.getState())
                .build();
    }

    @PostMapping("/{restaurantId}/menus")
    public void createMenuById(@PathVariable String restaurantId
            , @RequestBody MenuRequest menuRequest) {
        // 메뉴를 생성한다.
        menuService.createMenuById(MenuRegisterVO.convert(String.valueOf(UUID.randomUUID()), menuRequest));
    }

    @GetMapping("/{restaurantId}/menus")
    public List<MenuRequest> findMenuById(@PathVariable String restaurantId
            , @RequestBody MenuRequest menuRequest) {
        // 해당 레스토랑의 메뉴들을 조회한다.
        List<MenuVO> menuOutputList = menuService.findMenuById(MenuVO.convert(menuRequest.getMenuId(), menuRequest));

        List<MenuRequest> MenuResponseList = new ArrayList<>();
        for (MenuVO menuOutput : menuOutputList) {
            MenuResponseList.add(MenuRequest.builder()
                    .menuId(menuOutput.getMenuId())
                    .restaurantId(menuOutput.getRestaurantId())
                    .name(menuOutput.getName())
                    .state(menuOutput.getState())
                    .build());
        }

        return MenuResponseList;
    }

    @PutMapping("/{restaurantId}/menus/{menuId}")
    public void modifyMenuById(@PathVariable String restaurantId
            , @PathVariable String menuId
            , @RequestBody MenuRequest menuRequest) {
        // 메뉴 정보를 수정한다.
        menuService.modifyMenuById(MenuVO.convert(menuId, menuRequest));
    }

    @GetMapping("/{restaurantId}/menus/{menuId}")
    public List<SubOptionRequest> findSubOptions(@PathVariable String menuId
            , @RequestBody RestaurantMenuOptionRequest restaurantMenuOptionRequest) {
        // 해당 메뉴의 하위옵션들을 조회한다.
        List<OptionVO> optionOutputList = optionService.findOptionById(RestaurantMenuOptionVO.convert(restaurantMenuOptionRequest));

        List<SubOptionVO> subOptionOutputList = new ArrayList<>();
        for (OptionVO optionOutput : optionOutputList) {
            subOptionOutputList.addAll(subOptionService.findSubOptionById(RestaurantMenuSubOptionVO.convert(optionOutput.getMenuId(), optionOutput.getOptionId())));
        }

        if (ObjectUtils.isEmpty(subOptionOutputList)) {
            log.warn("subOptions 테이블 조회결과 없음");
            return Collections.emptyList();
        }

        List<SubOptionRequest> subOptionResponseList = new ArrayList<>();
        for (SubOptionVO subOptionOutput : subOptionOutputList) {
            subOptionResponseList.add(SubOptionRequest.builder()
                    .menuId(subOptionOutput.getMenuId())
                    .optionId(subOptionOutput.getOptionId())
                    .subOptionId(subOptionOutput.getSubOptionId())
                    .name(subOptionOutput.getName())
                    .price(subOptionOutput.getPrice())
                    .build());
        }
        log.info(String.format("subOptions 테이블 조회성공. 조회결과 count=[{%s}]", subOptionResponseList.size()));

        return subOptionResponseList;
    }
}