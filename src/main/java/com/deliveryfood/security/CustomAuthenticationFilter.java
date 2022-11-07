package com.deliveryfood.security;

import com.deliveryfood.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(request.getParameter("username") == null || request.getParameter("password") == null)
            return null;

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        request.getParameter("username"),
                        request.getParameter("password"));

        setDetails(request, authenticationToken);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication");

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        if(userDetails.getAuthority().equals("ROLE_NOT_AUTH")) {
            response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
            // response에 리다이렉트 URI 추가 가능
        }
    }
}
