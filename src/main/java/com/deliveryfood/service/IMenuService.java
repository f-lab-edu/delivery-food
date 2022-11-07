package com.deliveryfood.service;

import com.deliveryfood.dto.MenuDto;
import com.deliveryfood.controller.model.request.MenuRequest;

import java.util.List;

public interface IMenuService {

    void createMenuById(MenuRequest menuRequest);
    MenuDto findMenus(MenuRequest menuRequest);
    List<MenuDto> findMenuById(MenuRequest menuRequest);
    void modifyMenuById(MenuRequest menuRequest);
}