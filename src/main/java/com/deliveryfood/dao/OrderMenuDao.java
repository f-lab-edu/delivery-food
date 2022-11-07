package com.deliveryfood.dao;

import com.deliveryfood.dto.OrderMenuDto;
import com.deliveryfood.controller.model.request.OrderMenuRequest;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderMenuDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    public OrderMenuRequest findOrderMenuById(OrderMenuDto orderMenuDto) {
        return sqlSessionTemplate.selectOne("com.deliveryfood.mapper.OrderMenuMapper.findOrderMenuById", orderMenuDto);
    }

    public void createOrderMenuById(OrderMenuDto orderMenuDto) {
        sqlSessionTemplate.selectOne("com.deliveryfood.mapper.OrderMenuMapper.createOrderMenuById", orderMenuDto);
    }

    public void deleteOrderMenuById(OrderMenuDto orderMenuDto) {
        sqlSessionTemplate.selectOne("com.deliveryfood.mapper.OrderMenuMapper.deleteOrderMenuById", orderMenuDto);
    }
}
