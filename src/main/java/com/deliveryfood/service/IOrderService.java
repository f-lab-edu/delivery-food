package com.deliveryfood.service;

import com.deliveryfood.service.model.OrderVO;

import java.util.List;

public interface IOrderService {

    void createOrder(OrderVO orderVO);
    OrderVO findOrder(OrderVO orderVO);
    List<OrderVO> findOrderById(OrderVO orderVO);
    void modifyOrderById(OrderVO orderVO);
}