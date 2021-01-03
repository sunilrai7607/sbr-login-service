package com.sbr.platform.api.config;

import com.sbr.platform.api.filter.SessionCookieFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginAppConfig {

    @Bean
    public FilterRegistrationBean registerFilters(SessionCookieFilter sessionCookieFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(sessionCookieFilter);
        filterRegistrationBean.addUrlPatterns("/api/v1/login-service/secure/*");
        filterRegistrationBean.setOrder(10);
        return filterRegistrationBean;
    }
}
