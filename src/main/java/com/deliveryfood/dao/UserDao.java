package com.deliveryfood.dao;

import com.deliveryfood.dto.MemberDto;
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

    public UserDto findByEmail(String email) {
        return sqlSessionTemplate.selectOne("com.deliveryfood.mapper.UserMapper.findByEmail", email);
    }

    public UserDto findByUserId(String userId) {
        return sqlSessionTemplate.selectOne("com.deliveryfood.mapper.UserMapper.findByUserId", userId);
    }

    public void updateUser(UserDto userDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.UserMapper.updateUser", userDto);
    }
}
