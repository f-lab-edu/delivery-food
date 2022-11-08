package com.deliveryfood.mapper;

import com.deliveryfood.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void register(UserDto userDto);

    UserDto findByUserId(String userId);

    void updateUser(UserDto userDto);

    void deleteUserByUserId(String userId);
}
