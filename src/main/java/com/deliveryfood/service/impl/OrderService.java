package com.deliveryfood.service.impl;

import com.deliveryfood.dto.OrderDto;
import com.deliveryfood.mapper.OrderMapper;
import com.deliveryfood.service.IOrderService;
import com.deliveryfood.service.model.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {

    private final OrderMapper orderMapper;

    @Override
    public void createOrder(OrderVO orderVO) {
        OrderDto orderDto = OrderDto.builder()
                .orderId(orderVO.getOrderId())
                .userId(orderVO.getUserId())
                .state(orderVO.getState())
                .build();
        orderMapper.createOrder(orderDto);
    }

    @Override
    public OrderVO findOrder(OrderVO orderVO) {
        OrderDto orderDto = OrderDto.builder()
                .orderId(orderVO.getOrderId())
                .build();
        OrderDto orderDtoOutput = orderMapper.findOrder(orderDto);

        return OrderVO.builder()
                .orderId(orderDtoOutput.getOrderId())
                .userId(orderDtoOutput.getUserId())
                .state(orderDtoOutput.getState())
                .build();
    }

    @Override
    public List<OrderVO> findOrderById(OrderVO orderVO) {
        OrderDto orderDto = OrderDto.builder()
                .userId(orderVO.getUserId())
                .build();
        List<OrderDto> orderDtoOutputList = orderMapper.findOrderById(orderDto);

        List<OrderVO> orderOutputList = new ArrayList<>();
        for (OrderDto orderDtoOutput : orderDtoOutputList) {
            orderOutputList.add(OrderVO.builder()
                    .orderId(orderDtoOutput.getOrderId())
                    .userId(orderDtoOutput.getUserId())
                    .state(orderDtoOutput.getState())
                    .build());
        }

        return orderOutputList;
    }

    @Override
    public void modifyOrderById(OrderVO orderVO) {
        OrderDto orderDto = OrderDto.builder()
                .orderId(orderVO.getOrderId())
                .state(orderVO.getState())
                .build();
        orderMapper.modifyOrderById(orderDto);
    }
}
