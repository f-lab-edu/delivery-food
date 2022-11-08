package com.deliveryfood.dao;

import com.deliveryfood.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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

    public MemberDto findByUserId(String userId) {
        return sqlSessionTemplate.selectOne("com.deliveryfood.mapper.MemberMapper.findByUserId", userId);
    }

    public void updateStatus(MemberDto memberDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.MemberMapper.updateStatus", memberDto);
    }

    public void updateRole(MemberDto memberDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.MemberMapper.updateRole", memberDto);
    }

    public void updateMember(MemberDto memberDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.MemberMapper.updateUser", memberDto);
    }

    public void deleteMemberByUserId(String userId) {
        sqlSessionTemplate.delete("com.deliveryfood.mapper.MemberMapper.deleteMemberByUserId", userId);
    }
}
