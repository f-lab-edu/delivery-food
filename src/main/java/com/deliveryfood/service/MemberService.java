package com.deliveryfood.service;

import com.deliveryfood.Util.MemberSession;
import com.deliveryfood.dao.MemberDao;
import com.deliveryfood.dto.MemberDto;
import com.deliveryfood.model.UserInput;
import com.deliveryfood.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberDao memberDao;
    private final MemberSession session;

    public static final String REGISTER_CODE = "FLAB";

    public boolean certification(UserRequest userRequest, String code) {
        MemberDto memberDto = memberDao.findByEmail(userRequest.getEmail());
        if(memberDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        if(!code.equals(REGISTER_CODE)) {
            // 인증 코드가 다름
            return false;
        }

        memberDto.setStatus(MemberDto.Status.REGISTER);
        memberDao.updateStatus(memberDto);
        return true;
    }

    public boolean register(UserInput userInput, String uuid) {
        if(memberDao.findByEmail(userInput.getEmail()) != null) {
            // 중복 유저 존재
            return false;
        }

        // 비밀번호 암호화
        String hashPw = BCrypt.hashpw(userInput.getPassword(), BCrypt.gensalt());

        MemberDto memberDto = MemberDto.builder()
                .userId(uuid)
                .name(userInput.getName())
                .email(userInput.getEmail())
                .password(hashPw)
                .phone(userInput.getPhone())
                .status(MemberDto.Status.REGISTER_AUTH)
                .regDt(LocalDateTime.now())
                .build();

        memberDao.register(memberDto);
        return true;
    }

    public boolean withdraw(UserRequest userRequest) {
        MemberDto memberDto = memberDao.findByEmail(userRequest.getEmail());
        if(memberDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        if(!BCrypt.checkpw(userRequest.getPassword(), memberDto.getPassword())) {
            // 비밀번호가 다름
            return false;
        }

        memberDto.setStatus(MemberDto.Status.WITHDRAW);
        memberDao.updateStatus(memberDto);
        return true;
    }

    public boolean login(UserRequest userRequest) {
        MemberDto memberDto = memberDao.findByEmail(userRequest.getEmail());
        if(memberDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        if(memberDto.getStatus().equals(MemberDto.Status.REGISTER_AUTH)) {
            // 본인 인증 완료 전
            return false;
        }

        if(!BCrypt.checkpw(userRequest.getPassword(), memberDto.getPassword())) {
            // 비밀번호가 다름
            return false;
        }

        session.setLoginUserId(memberDto.getUserId());
        return true;
    }

    public boolean modifyUser(UserInput userInput) {
        MemberDto memberDto = memberDao.findByEmail(userInput.getEmail());
        if(memberDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        memberDto.setPhone(userInput.getPhone());
        memberDao.updateUser(memberDto);
        return true;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto memberDto = memberDao.findByEmail(username);
        if(memberDto == null) {
            // 유저가 존재하지 않음
            return null;
        }

        if(memberDto.getStatus().equals(MemberDto.Status.REGISTER_AUTH)) {
            // 본인 인증 완료 전
            return null;
        }

        return new User(memberDto.getEmail(), memberDto.getPassword(), Collections.emptyList());
    }
}
