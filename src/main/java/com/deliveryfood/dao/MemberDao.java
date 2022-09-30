package com.deliveryfood.dao;

import com.deliveryfood.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    public boolean register(MemberDto memberDto) {
        sqlSessionTemplate.insert("com.deliveryfood.mapper.MemberMapper.register", memberDto);
        return true;
    }

    public MemberDto findByEmail(String email) {
        return sqlSessionTemplate.selectOne("com.deliveryfood.mapper.MemberMapper.findByEmail", email);
    }

    public void updateStatus(MemberDto memberDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.MemberMapper.updateStatus", memberDto);
    }

    public void updateUser(MemberDto memberDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.MemberMapper.updateUser", memberDto);
    }
}
