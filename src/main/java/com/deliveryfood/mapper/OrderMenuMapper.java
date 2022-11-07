package com.deliveryfood.mapper;


import com.deliveryfood.dto.OrderMenuDto;
import com.deliveryfood.controller.model.request.OrderMenuRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMenuMapper {

    OrderMenuRequest findOrderMenuById(OrderMenuDto orderMenuDto);
    void createOrderMenuById(OrderMenuDto orderMenuDto);
    void deleteOrderMenuById(OrderMenuDto orderMenuDto);
}