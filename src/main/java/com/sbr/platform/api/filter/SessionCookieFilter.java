package com.sbr.platform.api.filter;

import com.sbr.platform.api.model.domain.SharedSessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class SessionCookieFilter implements Filter {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionCookieFilter(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filter Initialization");
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter Request processing");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String sessionId = Optional.ofNullable(httpServletRequest.getRequestedSessionId()).orElseThrow(() -> new RuntimeException("No sessionId passed in request"));
        final Session session = sessionRepository.findById(sessionId);
        if (session != null) {
            SharedSessionContext sharedSessionContext = (SharedSessionContext) Optional.ofNullable(session.getAttribute("sharedSessionContext")).orElseThrow(() -> new RuntimeException("SharedSessionContext not found"));
            //Assert.notNull(sharedSessionContext.getAuthorizedContext(), "No AuthorizedContext found");
            HttpServletResponse httpServletResponse = ((HttpServletResponse) response);
            httpServletResponse.addHeader("X-USER-AUTHORIZATION", sharedSessionContext.toString());//.getAuthorizedContext().getToken()
            log.info("Got the sharedSession : {}", httpServletResponse.getHeaders("X-USER-AUTHORIZATION"));
        } else {
            log.error("No sessionId was found in Redis");
        }
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        log.info("filter Destruction");
    }
}
