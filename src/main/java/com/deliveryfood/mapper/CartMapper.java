package com.deliveryfood.mapper;

import com.deliveryfood.dto.CartMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CartMapper {

    List<CartMenuDto> findCart();

    void deleteCart(String userId);

    void addMenu(CartMenuDto cartMenuDto);

    void deleteMenu(Map<String, Object> map);

    void deleteAllCart();
}
