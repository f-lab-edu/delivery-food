package com.deliveryfood.common.mock.auth;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthMemberSecurityContextFactory.class)
public @interface WithAuthMember {
    String username();
    String authority();
}
