package com.deliveryfood.controller;

import com.deliveryfood.controller.model.request.OrderRequest;
import com.deliveryfood.service.IOrderService;
import com.deliveryfood.service.model.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
        OrderVO orderInput = OrderVO.builder()
                .orderId(String.valueOf(UUID.randomUUID()))
                .userId(String.valueOf(UUID.randomUUID()))
                .state("0")
                .build();
        orderService.createOrder(orderInput);
    }

    @GetMapping("/{orderId}")
    public OrderRequest findOrder(@PathVariable String orderId) {
        // 주문 조회
        OrderVO orderInput = OrderVO.builder()
                .orderId(orderId)
                .build();
        OrderVO orderOutput = orderService.findOrder(orderInput);

        return OrderRequest.builder()
                .orderId(orderOutput.getOrderId())
                .userId(orderOutput.getUserId())
                .state(orderOutput.getState())
                .build();
    }

    @GetMapping("/{userId}/orders")
    public List<OrderRequest> findOrderById(@PathVariable String userId) {
        // 유저의 모든 주문을 조회한다.
        OrderVO orderInput = OrderVO.builder()
                .userId(userId)
                .build();
        List<OrderVO> orderOutputList = orderService.findOrderById(orderInput);

        List<OrderRequest> orderResponseList = new ArrayList<>();
        for (OrderVO orderOutput : orderOutputList) {
            orderResponseList.add(OrderRequest.builder()
                    .orderId(orderOutput.getOrderId())
                    .userId(orderOutput.getUserId())
                    .state(orderOutput.getState())
                    .build());
        }

        return orderResponseList;
    }

    @PutMapping("/{orderId}")
    public void modifyOrderById(@PathVariable String orderId
            , @RequestBody OrderRequest orderRequest) {
        // 주문 수정
        OrderVO orderInput = OrderVO.builder()
                .orderId(orderId)
                .state(orderRequest.getState())
                .build();
        orderService.modifyOrderById(orderInput);
    }
}