package com.sbr.platform.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SessionIdNotFound extends RuntimeException {


    private static final long serialVersionUID = -5593456167562902187L;

    public SessionIdNotFound() {
        this("SessionId not found!");
    }

    public SessionIdNotFound(String message) {
        this(message, null);
    }

    public SessionIdNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
