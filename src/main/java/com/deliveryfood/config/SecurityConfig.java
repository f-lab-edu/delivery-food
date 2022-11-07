package com.deliveryfood.config;

import com.deliveryfood.security.CustomAuthenticationFilter;
import com.deliveryfood.security.CustomAuthenticationProvider;
import com.deliveryfood.security.CustomLogoutSuccessHandler;
import com.deliveryfood.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final IMemberService memberService;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        http.apply(new CustomHttpSecurity());

        http.authorizeRequests()
                .antMatchers(
                        "/"
                        , "/users/register"
                        , "/users/certification"
                        , "/riders/register"
                        , "/riders/certification"
                        , "/restaurants/register"
                        , "/restaurants/certification"
                )
                .permitAll()
                .antMatchers("/users/**", "/carts/**")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/restaurants/**")
                .access("hasRole('ROLE_RESTAURANT')")
                .antMatchers("/riders/**")
                .access("hasRole('ROLE_RIDER')");

        http.formLogin().disable();
        http.logout().logoutSuccessHandler(logoutSuccessHandler);

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class CustomHttpSecurity extends AbstractHttpConfigurer<CustomHttpSecurity, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilter(new CustomAuthenticationFilter(authenticationManager));
            http.authenticationProvider(new CustomAuthenticationProvider(memberService, getPasswordEncoder()));
        }
    }
}
