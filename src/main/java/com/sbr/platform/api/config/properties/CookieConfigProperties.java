package com.sbr.platform.api.config.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@Data
@ConfigurationProperties(prefix = "sbr.service.cookie-config")
public class CookieConfigProperties {

    private Boolean httpOnly;

    private Boolean secure;

    private String domain;

    private String path;

    private String trlSessionId;

    private String userCookieName;

    @PostConstruct
    public void printProperties() {
        log.info("CookieConfigProperties : config : {} ", this);
    }

}
