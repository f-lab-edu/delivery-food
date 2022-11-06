package com.deliveryfood.common.mock.auth;

import com.deliveryfood.model.CustomUserDetails;
import com.deliveryfood.model.request.UserRegisterRequest;
import com.deliveryfood.service.IMemberService;
import com.deliveryfood.service.IUserService;
import com.deliveryfood.vo.UserRegisterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class WithAuthMemberSecurityContextFactory implements WithSecurityContextFactory<WithAuthMember> {

    private final IMemberService memberService;
    private final IUserService userService;

    @Override
    public SecurityContext createSecurityContext(WithAuthMember annotation) {
        String username = annotation.username();
        String password = annotation.password();
        String authority = annotation.authority();

        if(authority.contains("ROLE_USER")) {
            UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                    .name(username)
                    .email(username)
                    .password(password)
                    .phone("010-1234-5678")
                    .address("서울시 구로구 디지털로")
                    .build();

            userService.register(UserRegisterVO.convert(registerRequest));
        }

        CustomUserDetails user = (CustomUserDetails) memberService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), getAuthorities(authority));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String authority) {
        ArrayList<GrantedAuthority> authorityList = new ArrayList<>();
        for(String role : authority.split(",")) {
            authorityList.add(new SimpleGrantedAuthority(role));
        }
        return authorityList;
    }
}
