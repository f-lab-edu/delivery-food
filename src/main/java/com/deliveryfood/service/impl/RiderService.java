package com.deliveryfood.service.impl;

import com.deliveryfood.dao.RiderDao;
import com.deliveryfood.dto.MemberDto;
import com.deliveryfood.dto.RiderDto;
import com.deliveryfood.controller.model.request.UserRequest;
import com.deliveryfood.service.IMemberService;
import com.deliveryfood.service.IRiderService;
import com.deliveryfood.service.model.RiderRegisterVO;
import com.deliveryfood.service.model.RiderUpdateVO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RiderService implements IRiderService {

    private final IMemberService memberService;
    private final RiderDao riderDao;

    public RiderService(IMemberService memberService, RiderDao riderDao) {
        this.memberService = memberService;
        this.riderDao = riderDao;
    }

    @Override
    public boolean certification(String userId, String code) {
        // REGISTER_CODE 와 일치하면 인증 완료
        memberService.certification(userId, code);
        RiderDto riderDto = riderDao.findByUserId(userId);
        if(riderDto == null) {
            throw new UsernameNotFoundException("Rider DB에 User가 존재하지 않음 : " + userId);
        }

        return true;
    }

    @Override
    public boolean register(RiderRegisterVO registerVO) {
        String uuid = UUID.randomUUID().toString();
        memberService.register(registerVO, uuid, MemberDto.Role.ROLE_RIDER);
        if(riderDao.findByUserId(uuid) != null) {
            throw new RuntimeException("Rider DB에 중복 유저가 존재함");
        }

        RiderDto riderDto = RiderDto.builder()
                .userId(uuid)
                .commission(registerVO.getCommission())
                .status(RiderDto.Status.NONE)
                .regDt(LocalDateTime.now())
                .udtDt(LocalDateTime.now())
                .build();

        riderDao.register(riderDto);
        return true;
    }

    @Override
    public boolean withdraw(String userId, UserRequest userRequest) {
        memberService.withdraw(userRequest);
        RiderDto riderDto = riderDao.findByUserId(userId);
        if(riderDto == null) {
            throw new UsernameNotFoundException("Rider DB에 User가 존재하지 않음 : " + userId);
        }

        return true;
    }

    @Override
    public boolean modifyRider(String userId, RiderUpdateVO updateVO) {
        RiderDto riderDto = riderDao.findByUserId(userId);
        if(riderDto == null) {
            throw new UsernameNotFoundException("Rider DB에 User가 존재하지 않음 : " + userId);
        }

        riderDto.setCommission(updateVO.getCommission());
        riderDao.updateRider(riderDto);
        return true;
    }

    @Override
    public void deleteRiderByEmail(String email) {
        String userId = memberService.getUserId(email);

        memberService.deleteMemberByUserId(userId);
        riderDao.deleteRiderByUserId(userId);
    }
}
