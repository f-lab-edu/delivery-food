package com.deliveryfood.controller;

import com.deliveryfood.dto.CartMenuDto;
import com.deliveryfood.controller.model.request.CartMenuRequest;
import com.deliveryfood.controller.model.response.CartMenuResponse;
import com.deliveryfood.service.impl.CartService;
import com.deliveryfood.util.SecurityPrincipal;
import com.deliveryfood.service.model.CartMenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartMenuResponse>> findCart() {
        // 장바구니 조회
        List<CartMenuDto> menuList = cartService.findCart(SecurityPrincipal.getLoginUserId());
        return new ResponseEntity<>(menuList.stream()
                .map(CartMenuResponse::convert)
                .collect(Collectors.toList())
                , HttpStatus.OK);

    }

    @DeleteMapping
    public void deleteCart() {
        // 장바구니 삭제
        cartService.deleteCart(SecurityPrincipal.getLoginUserId());
    }

    @PostMapping("/add")
    public void addMenu(@RequestBody CartMenuRequest menuRequest) {
        // 장바구니 메뉴 추가
        cartService.addMenu(CartMenuVO.convert(SecurityPrincipal.getLoginUserId(), menuRequest));
    }

    @PostMapping("/sub/{index}")
    public void deleteMenu(@PathVariable int index) {
        // 장바구니 메뉴 삭제
        cartService.deleteMenu(SecurityPrincipal.getLoginUserId(), index);
    }
}
