package com.sbr.platform.api.service.impl;

import com.sbr.platform.api.config.properties.CookieConfigProperties;
import com.sbr.platform.api.exception.SessionIdNotFound;
import com.sbr.platform.api.model.domain.AuthorizedContext;
import com.sbr.platform.api.model.domain.SharedSessionContext;
import com.sbr.platform.api.model.request.LoginRequest;
import com.sbr.platform.api.service.LoginService;
import com.sbr.platform.api.utility.SessionUtility;
import com.sbr.platform.api.utility.TokenUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private static final String ISSUER_TOKEN = "sbr-security";
    private final RedisCacheManager redisCacheManager;
    private final CookieConfigProperties cookieConfigProperties;
    private final SessionRepository sessionRepository;

    @Autowired
    public LoginServiceImpl(RedisCacheManager redisCacheManager, CookieConfigProperties cookieConfigProperties, SessionRepository sessionRepository) {
        this.redisCacheManager = redisCacheManager;
        this.cookieConfigProperties = cookieConfigProperties;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public ResponseEntity<SharedSessionContext> initiateSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = SessionUtility.createSession(request);
        SharedSessionContext sharedSessionContext = createSharedSessionContext(httpSession);
        httpSession.setAttribute("sharedSessionContext", sharedSessionContext);
        log.info("Session Created : {} ", sharedSessionContext.getTlsSessionId());
        SessionUtility.createCookies(response, sharedSessionContext.getTlsSessionId(), cookieConfigProperties);
        return ResponseEntity.ok(sharedSessionContext);
    }


    @Override
    public ResponseEntity<?> getSharedSession(final String sessionId) {
        SharedSessionContext sharedSessionContext = null;
        Session session = Optional.ofNullable(sessionRepository.findById(sessionId)).orElseThrow(() -> new SessionIdNotFound("Invalid sessionId : " + sessionId));
        Object sessionAttribute = session.getAttribute("sharedSessionContext");
        if (sessionAttribute instanceof SharedSessionContext) {
            sharedSessionContext = (SharedSessionContext) sessionAttribute;
            log.info("Shared SessionContext : {} ", sharedSessionContext);
            return ResponseEntity.ok(sharedSessionContext);
        }
        log.error("Session.sharedSessionContext : {} ", sessionAttribute);
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<SharedSessionContext> login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        Session session = sessionRepository.findById(request.getSession().getId());
        Object sessionAttribute = session.getAttribute("sharedSessionContext");
        if (sessionAttribute instanceof SharedSessionContext) {
            SharedSessionContext sharedSessionContext = (SharedSessionContext) sessionAttribute;
            addAuthorizedToken(sharedSessionContext);
            session.setAttribute("sharedSessionContext", sharedSessionContext);
            sessionRepository.save(session);
            return ResponseEntity.ok(sharedSessionContext);
        }
        return ResponseEntity.badRequest().build();
    }

    private SharedSessionContext createSharedSessionContext(final HttpSession httpSession) {
        return new SharedSessionContext(httpSession);
    }

    private void addAuthorizedToken(final SharedSessionContext sharedSessionContext) {
        String token = TokenUtility.createJWT(sharedSessionContext.getTlsSessionId(), ISSUER_TOKEN, "test-profile", 6000);
        log.info("Token created : {} ", token);
        sharedSessionContext.setAuthorizedContext(new AuthorizedContext(token));
    }
}
