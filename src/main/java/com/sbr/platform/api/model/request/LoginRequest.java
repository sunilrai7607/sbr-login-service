package com.sbr.platform.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {


    private static final long serialVersionUID = -3691090834414061715L;

    @NotNull
    private String userId;

    @NotNull
    private String password;
}
