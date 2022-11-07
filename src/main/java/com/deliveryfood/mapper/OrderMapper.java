package com.deliveryfood.mapper;


import com.deliveryfood.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    void createOrder(OrderDto orderDto);
    OrderDto findOrder(OrderDto orderDto);
    List<OrderDto> findOrderById(OrderDto orderDto);
    void modifyOrderById(OrderDto orderDto);
}