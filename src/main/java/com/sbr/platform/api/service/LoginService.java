package com.sbr.platform.api.service;

import com.sbr.platform.api.model.domain.SharedSessionContext;
import com.sbr.platform.api.model.request.LoginRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    ResponseEntity<SharedSessionContext> initiateSession(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> getSharedSession(final String sessionId);

    ResponseEntity<SharedSessionContext> login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response);
}
