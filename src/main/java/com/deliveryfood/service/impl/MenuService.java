package com.deliveryfood.service.impl;

import com.deliveryfood.dto.MenuDto;
import com.deliveryfood.mapper.MenuMapper;
import com.deliveryfood.controller.model.request.MenuRequest;
import com.deliveryfood.service.IMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService implements IMenuService {

    private final MenuMapper menuMapper;

    @Override
    public void createMenuById(MenuRequest menuRequest) {
        MenuDto menuDto = MenuDto.builder()
                .menuId(menuRequest.getMenuId())
                .restaurantId(menuRequest.getRestaurantId())
                .name(menuRequest.getName())
                .build();
        menuMapper.createMenuById(menuDto);
    }

    @Override
    public MenuDto findMenus(MenuRequest menuRequest) {
        MenuDto menuDto = MenuDto.builder()
                .menuId(menuRequest.getMenuId())
                .restaurantId(menuRequest.getRestaurantId())
                .build();
        return menuMapper.findMenus(menuDto);
    }

    @Override
    public List<MenuDto> findMenuById(MenuRequest menuRequest) {
        MenuDto menuDto = MenuDto.builder()
                .restaurantId(menuRequest.getRestaurantId())
                .build();
        return menuMapper.findMenuById(menuDto);
    }

    @Override
    public void modifyMenuById(MenuRequest menuRequest) {
        MenuDto menuDto = MenuDto.builder()
                .restaurantId(menuRequest.getRestaurantId())
                .menuId(menuRequest.getMenuId())
                .name(menuRequest.getName())
                .build();
        menuMapper.modifyMenuById(menuDto);
    }


}