package com.example.cms.controller.admin;

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
