package com.deliveryfood.dao;

import com.deliveryfood.dto.RiderDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RiderDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    public boolean register(RiderDto riderDto) {
        sqlSessionTemplate.insert("com.deliveryfood.mapper.RiderMapper.register", riderDto);
        return true;
    }

    public RiderDto findByUserId(String userId) {
        return sqlSessionTemplate.selectOne("com.deliveryfood.mapper.RiderMapper.findByUserId", userId);
    }

    public void updateRider(RiderDto riderDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.RiderMapper.updateRider", riderDto);
    }

    public void updateStatus(RiderDto riderDto) {
        sqlSessionTemplate.update("com.deliveryfood.mapper.RiderMapper.updateStatus", riderDto);
    }

    public void deleteRiderByUserId(String userId) {
        sqlSessionTemplate.delete("com.deliveryfood.mapper.RiderMapper.deleteRiderByUserId", userId);
    }
}
