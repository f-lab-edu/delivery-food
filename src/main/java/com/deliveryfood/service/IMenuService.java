package com.deliveryfood.service;

import com.deliveryfood.dto.MenuDto;
import com.deliveryfood.service.model.MenuRegisterVO;
import com.deliveryfood.service.model.MenuVO;

import java.util.List;

public interface IMenuService {

    void createMenuById(MenuRegisterVO menuRegisterVO);
    List<MenuDto> findMenuById(MenuVO menuVO);
    void modifyMenuById(MenuVO menuVO);
    void deleteByMenuId(String menuId);
}