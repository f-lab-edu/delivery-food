package com.deliveryfood.security;

import com.deliveryfood.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@Getter
public class CustomUserDetails implements UserDetails {

    private String userId;
    private String email;
    private String password;
    private String authority;
    private MemberDto.Status status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorityList = new ArrayList<>();
        for(String role : authority.split(",")) {
            authorityList.add(new SimpleGrantedAuthority(role));
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
