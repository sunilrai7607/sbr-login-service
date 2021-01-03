package com.sbr.platform.api.controller;

import com.sbr.platform.api.model.domain.SharedSessionContext;
import com.sbr.platform.api.model.request.LoginRequest;
import com.sbr.platform.api.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "api/v1/login-service")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/session/initiate")
    public ResponseEntity<SharedSessionContext> initiateSession(HttpServletRequest request, HttpServletResponse response) {
        return loginService.initiateSession(request, response);
    }

    @PostMapping(value = "/secure/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SharedSessionContext> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        return loginService.login(loginRequest, request, response);
    }

    @GetMapping("/secure/{sessionId}")
    public ResponseEntity<?> initiateSession(@PathVariable("sessionId") final String sessionId) {
        return loginService.getSharedSession(sessionId);
    }
}
