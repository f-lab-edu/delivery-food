package com.deliveryfood.service;

import com.deliveryfood.dao.MemberDao;
import com.deliveryfood.dao.RiderDao;
import com.deliveryfood.dto.MemberDto;
import com.deliveryfood.dto.RiderDto;
import com.deliveryfood.model.CustomUserDetails;
import com.deliveryfood.vo.RiderRegisterVO;
import com.deliveryfood.model.request.UserRequest;
import com.deliveryfood.vo.RiderUpdateVO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RiderService extends MemberService implements IRiderService {

    private final RiderDao riderDao;

    public RiderService(MemberDao memberDao, RiderDao riderDao) {
        super(memberDao);
        this.riderDao = riderDao;
    }

    @Override
    public boolean certification(String userId, String code) {
        // REGISTER_CODE 와 일치하면 인증 완료
        super.certification(userId, code);
        RiderDto riderDto = riderDao.findByUserId(userId);
        if(riderDto == null) {
            throw new UsernameNotFoundException("Rider DB에 User가 존재하지 않음 : " + userId);
        }

        return true;
    }

    @Override
    public boolean register(RiderRegisterVO registerVO) {
        String uuid = UUID.randomUUID().toString();
        super.register(registerVO, uuid, MemberDto.Role.ROLE_RIDER);
        if(riderDao.findByUserId(uuid) != null) {
            throw new RuntimeException("Rider DB에 중복 유저가 존재함");
        }

        RiderDto riderDto = RiderDto.builder()
                .userId(uuid)
                .commission(registerVO.getCommission())
                .status(RiderDto.Status.NONE)
                .regDt(LocalDateTime.now())
                .build();

        riderDao.register(riderDto);
        return true;
    }

    @Override
    public boolean withdraw(String userId, UserRequest userRequest) {
        super.withdraw(userRequest);
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
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return super.loadUserByUsername(username);
    }
}
