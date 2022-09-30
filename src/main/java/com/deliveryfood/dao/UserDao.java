package com.deliveryfood.dao;

import com.deliveryfood.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    public boolean register(UserDto userDto) {
        sqlSessionTemplate.insert("com.deliveryfood.mapper.UserMapper.register", userDto);
        return true;
    }

    public UserDto findById(String email) {
        return sqlSessionTemplate.selectOne("com.deliveryfood.mapper.UserMapper.findById", email);
    }

    public void updateUser(UserDto userDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.UserMapper.updateUser", userDto);
    }
}
