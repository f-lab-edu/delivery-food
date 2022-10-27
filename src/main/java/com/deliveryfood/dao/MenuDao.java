package com.deliveryfood.dao;

import com.deliveryfood.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    public void createMenuById(MenuDto menuDto) {
        sqlSessionTemplate.insert("com.deliveryfood.mapper.MenuMapper.createMenuById", menuDto);
    }

    public MenuDto findMenus(MenuDto menuDto) {
        return sqlSessionTemplate.selectOne("com.deliveryfood.mapper.MenuMapper.findMenus");
    }

    public List<MenuDto> findMenuById(MenuDto menuDto) {
        return sqlSessionTemplate.selectList("com.deliveryfood.mapper.MenuMapper.findMenuById", menuDto.getRestaurantId());
    }

    public void modifyMenuById(MenuDto menuDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.MenuMapper.modifyMenuById", menuDto);
    }
}
