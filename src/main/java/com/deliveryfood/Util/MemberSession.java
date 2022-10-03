package com.deliveryfood.Util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class MemberSession {

    private static final String USER_ID = "USER_ID";
    private final HttpSession session;

    public void setLoginUserId(String userId) {
        session.setAttribute(USER_ID, userId);
    }

    public String getLoginUserId() {
        return (String)session.getAttribute(USER_ID);
    }

    public void setLogoutUser() {
        session.removeAttribute(USER_ID);
    }
}
