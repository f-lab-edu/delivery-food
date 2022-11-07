package com.deliveryfood.service;

import com.deliveryfood.dto.OrderDto;
import com.deliveryfood.controller.model.request.OrderRequest;

import java.util.List;

public interface IOrderService {

    void createOrder(OrderRequest orderRequest);
    OrderDto findOrder(OrderRequest orderRequest);
    List<OrderDto> findOrderById(OrderRequest orderRequest);
    void modifyOrderById(OrderRequest orderRequest);
}