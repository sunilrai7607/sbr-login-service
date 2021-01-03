package com.sbr.platform.api.utility;

import com.sbr.platform.api.config.properties.CookieConfigProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUtility {

    public static void invalidateSession(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
    }

    public static HttpSession createSession(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        return httpServletRequest.getSession(true);
    }

    public static void createCookies(HttpServletResponse response, final String tlsSessionId, CookieConfigProperties cookieConfigProperties) {
        Cookie cookie = new Cookie(cookieConfigProperties.getTrlSessionId(), tlsSessionId);
        cookie.setSecure(cookieConfigProperties.getSecure());
        cookie.setHttpOnly(cookieConfigProperties.getHttpOnly());
        cookie.setDomain(cookieConfigProperties.getDomain());
        cookie.setPath(cookieConfigProperties.getPath());
        response.addCookie(cookie);
    }
}
