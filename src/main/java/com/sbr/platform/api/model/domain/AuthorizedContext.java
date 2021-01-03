package com.sbr.platform.api.model.domain;

import com.sbr.platform.api.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizedContext extends BaseModel {

    @NotNull
    private String token;
}
