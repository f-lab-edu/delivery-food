package com.deliveryfood.service.impl;

import com.deliveryfood.dao.CartDao;
import com.deliveryfood.dto.CartMenuDto;
import com.deliveryfood.service.ICartService;
import com.deliveryfood.service.model.CartMenuVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService implements ICartService {

    private final CartDao cartDao;

    private static final int MAX_CART_MENU_COUNT = 10;

    @Override
    public List<CartMenuDto> findCart(String userId) {
        List<CartMenuDto> cartList = cartDao.findCart(userId);
        if(cartList == null) {
            throw new NullPointerException("장바구니가 존재하지 않음 : " + userId);
        }

        return cartList;
    }

    @Override
    public void deleteCart(String userId) {
        List<CartMenuDto> cartList = cartDao.findCart(userId);
        if(cartList == null) {
            throw new NullPointerException("삭제할 장바구니가 존재하지 않음 : " + userId);
        }

        cartDao.deleteCart(userId);
    }

    @Override
    public void addMenu(CartMenuVO menuVO) {
        int totalCount = 0;
        List<CartMenuDto> cartList = cartDao.findCart(menuVO.getUserId());
        if(cartList != null) {
            totalCount = cartList.size();
        }

        if(MAX_CART_MENU_COUNT <= totalCount) {
            throw new IndexOutOfBoundsException("장바구니 최대 개수 초과 : " + MAX_CART_MENU_COUNT);
        }

        if(checkEqualMenu(cartList, menuVO)) {
            throw new RuntimeException("장바구니 메뉴 중복 : " + menuVO.getMenuId());
        }

        CartMenuDto cartMenuDto = CartMenuDto.builder()
                .userId(menuVO.getUserId())
                .index(menuVO.getIndex())
                .menuId(menuVO.getMenuId())
                .count(menuVO.getCount())
                .regDt(LocalDateTime.now())
                .build();

        cartDao.addMenu(cartMenuDto);
    }

    private boolean checkEqualMenu(List<CartMenuDto> menuList, CartMenuVO menuVO) {
        if(menuList == null) {
            log.debug("cartList is null.");
            return false;
        }
        //옵션 기능 추가 후, 옵션까지 비교하도록
        return menuList.stream().anyMatch(e -> Objects.equals(e.getMenuId(), menuVO.getMenuId()));
    }

    @Override
    public void deleteMenu(String userId, int index) {
        int totalCount = 0;
        List<CartMenuDto> cartList = cartDao.findCart(userId);
        if(cartList != null) {
            totalCount = cartList.size();
        }

        if(totalCount <= 0) {
            throw new RuntimeException("추가된 장바구니 메뉴가 없음");
        }

        if(index < 1 || MAX_CART_MENU_COUNT < index) {
            throw new IndexOutOfBoundsException("장바구니 메뉴 인덱스 범위 오류 : " + index);
        }

        if(cartList.stream().noneMatch(e -> Objects.equals(e.getIndex(), index))) {
            throw new RuntimeException("삭제할 장바구니 인덱스가 존재하지 않음 : " + index);
        }

        cartDao.deleteMenu(userId, index);
    }
}
