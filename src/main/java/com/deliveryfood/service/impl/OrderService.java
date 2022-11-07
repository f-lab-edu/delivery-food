package com.deliveryfood.service.impl;

import com.deliveryfood.dto.OrderDto;
import com.deliveryfood.mapper.OrderMapper;
import com.deliveryfood.controller.model.request.OrderRequest;
import com.deliveryfood.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {

    private final OrderMapper orderMapper;

    @Override
    public void createOrder(OrderRequest orderRequest) {
        OrderDto orderDto = OrderDto.builder()
                .orderId(orderRequest.getOrderId())
                .userId(orderRequest.getUserId())
                .state(orderRequest.getState())
                .build();
        orderMapper.createOrder(orderDto);
    }

    @Override
    public OrderDto findOrder(OrderRequest orderRequest) {
        OrderDto orderDto = OrderDto.builder()
                .orderId(orderRequest.getOrderId())
                .build();
        return orderMapper.findOrder(orderDto);
    }

    @Override
    public List<OrderDto> findOrderById(OrderRequest orderRequest) {
        OrderDto orderDto = OrderDto.builder()
                .userId(orderRequest.getUserId())
                .build();
        return orderMapper.findOrderById(orderDto);
    }

    @Override
    public void modifyOrderById(OrderRequest orderRequest) {
        OrderDto orderDto = OrderDto.builder()
                .orderId(orderRequest.getOrderId())
                .state(orderRequest.getState())
                .build();
        orderMapper.modifyOrderById(orderDto);
    }
}
