package com.deliveryfood.dao;

import com.deliveryfood.dto.CartMenuDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CartDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    public List<CartMenuDto> findCart(String userId) {
        return sqlSessionTemplate.selectList("com.deliveryfood.mapper.CartMapper.findCart", userId);
    }

    public void deleteCart(String userId) {
        sqlSessionTemplate.insert("com.deliveryfood.mapper.CartMapper.deleteCart", userId);
    }

    public void addMenu(CartMenuDto cartMenuDto) {
        sqlSessionTemplate.insert("com.deliveryfood.mapper.CartMapper.addMenu", cartMenuDto);
    }

    public void deleteMenu(String userId, int index) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("index", index);
        sqlSessionTemplate.delete("com.deliveryfood.mapper.CartMapper.deleteMenu", map);
    }

    public void deleteAllCart() {
        sqlSessionTemplate.insert("com.deliveryfood.mapper.CartMapper.deleteAllCart");
    }
}
