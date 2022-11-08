package com.deliveryfood.mapper;

import com.deliveryfood.dto.RiderDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RiderMapper {

    void register(RiderDto riderDto);

    RiderDto findByUserId(String userId);

    void updateRider(RiderDto riderDto);

    void updateStatus(RiderDto riderDto);

    void delteRiderByUserId(String userId);
}
