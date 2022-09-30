package com.deliveryfood.mapper;

import com.deliveryfood.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    MemberDto findByEmail(String email);

    void register(MemberDto userDto);

    void updateStatus(MemberDto userDto);

    void updateUser(MemberDto userDto);
}
