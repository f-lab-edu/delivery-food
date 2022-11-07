package com.deliveryfood.mapper;


import com.deliveryfood.dto.MenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    void createMenuById(MenuDto menuDto);
    MenuDto findMenus(MenuDto menuDto);
    List<MenuDto> findMenuById(MenuDto menuDto);
    void modifyMenuById(MenuDto menuDto);
}