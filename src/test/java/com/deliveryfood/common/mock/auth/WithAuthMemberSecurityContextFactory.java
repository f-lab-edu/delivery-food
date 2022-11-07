package com.deliveryfood.common.mock.auth;

import com.deliveryfood.security.CustomUserDetails;
import com.deliveryfood.service.IMemberService;
import com.deliveryfood.service.IRestaurantUserService;
import com.deliveryfood.service.IRiderService;
import com.deliveryfood.service.IUserService;
import com.deliveryfood.vo.RestaurantUserRegisterVO;
import com.deliveryfood.vo.RiderRegisterVO;
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
    private final IRiderService riderService;
    private final IRestaurantUserService restaurantUserService;

    @Override
    public SecurityContext createSecurityContext(WithAuthMember annotation) {
        String username = annotation.username();
        String password = annotation.password();
        String authority = annotation.authority();

        if(authority.contains("ROLE_USER")) {
            registerUser(username, password);
        } else if(authority.contains("ROLE_RIDER")) {
            registerRider(username, password);
        } else if(authority.contains("ROLE_RESTAURANT")) {
            registerRestaurant(username, password);
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

    private void registerUser(String username, String password) {
        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                .name(username)
                .email(username)
                .password(password)
                .phone("010-1234-5678")
                .address("서울시 구로구 디지털로")
                .build();
        userService.register(UserRegisterVO.convert(registerRequest));
    }

    private void registerRider(String username, String password) {
        RiderRegisterRequest registerRequest = RiderRegisterRequest.builder()
                .name(username)
                .email(username)
                .password(password)
                .phone("010-1234-5678")
                .commission(3000)
                .build();
        riderService.register(RiderRegisterVO.convert(registerRequest));
    }

    private void registerRestaurant(String username, String password) {
        RestaurantUserRegisterRequest registerRequest = RestaurantUserRegisterRequest.builder()
                .name(username)
                .email(username)
                .password(password)
                .phone("010-1234-5678")
                .build();
        restaurantUserService.register(RestaurantUserRegisterVO.convert(registerRequest));
    }
}
