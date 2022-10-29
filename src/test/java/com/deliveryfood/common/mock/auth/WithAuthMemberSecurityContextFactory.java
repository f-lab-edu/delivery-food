package com.deliveryfood.common.mock.auth;

import com.deliveryfood.model.CustomUserDetails;
import com.deliveryfood.service.IMemberService;
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

    @Override
    public SecurityContext createSecurityContext(WithAuthMember annotation) {
        String username = annotation.username();
        String authority = annotation.authority();
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