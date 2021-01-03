package com.sbr.platform.api.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sbr.platform.api.model.BaseModel;
import com.sbr.platform.api.utility.TokenUtility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SharedSessionContext extends BaseModel {

    @NotNull
    @JsonProperty("tls-session-id")
    private String tlsSessionId;

    private String sessionId;

    private AuthorizedContext authorizedContext;

    private Long expirations;

    private Long createdAt;

    public SharedSessionContext(final HttpSession httpSession) {
        this.tlsSessionId = TokenUtility.generateUUID().toString();
        this.sessionId = httpSession.getId();
        this.createdAt = httpSession.getCreationTime();
        this.expirations = httpSession.getLastAccessedTime();
    }
}
