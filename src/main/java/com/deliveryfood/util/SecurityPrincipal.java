package com.deliveryfood.util;

import com.deliveryfood.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityPrincipal {

    public static CustomUserDetails getCustomUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (CustomUserDetails)authentication.getPrincipal();
    }

    public static String getLoginUserId() {
        CustomUserDetails userDetails = getCustomUserDetails();
        return userDetails.getUserId();
    }
}
