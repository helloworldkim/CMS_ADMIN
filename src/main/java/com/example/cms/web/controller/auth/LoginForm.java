package com.example.cms.web.controller.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginForm {

    @NotBlank
    private String adminId;
    @NotBlank
    private String password;
}
