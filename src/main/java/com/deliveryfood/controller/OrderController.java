package com.deliveryfood.controller;

import com.deliveryfood.dto.OrderDto;
import com.deliveryfood.controller.model.request.OrderRequest;
import com.deliveryfood.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/checkout")
    public void createOrder() {
        // 주문 생성
        OrderRequest order = OrderRequest.builder()
                .orderId(String.valueOf(UUID.randomUUID()))
                .userId(String.valueOf(UUID.randomUUID()))
                .state("0")
                .build();
        orderService.createOrder(order);
    }

    @GetMapping("/{orderId}")
    public OrderDto findOrder(@PathVariable String orderId) {
        // 주문 조회
        OrderRequest order = OrderRequest.builder()
                .orderId(orderId)
                .build();
        return orderService.findOrder(order);
    }

    @GetMapping("/{userId}/orders")
    public List<OrderDto> findOrderById(@PathVariable String userId) {
        // 유저의 모든 주문을 조회한다.
        OrderRequest order = OrderRequest.builder()
                .userId(userId)
                .build();
        return orderService.findOrderById(order);
    }

    @PutMapping("/{orderId}")
    public void modifyOrderById(@PathVariable String orderId
            , @RequestBody OrderRequest orderRequest) {
        // 주문 수정
        OrderRequest order = OrderRequest.builder()
                .orderId(orderId)
                .state(orderRequest.getState())
                .build();
        orderService.modifyOrderById(order);
    }
}