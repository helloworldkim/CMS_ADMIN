package com.example.cms.web.controller.group;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.cms.system.constant.RegexConst.PWD_REGEX;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminGroupRegisterForm {
    @NotBlank(message = "{common.valid.required}")
    private String name;
    @NotBlank(message = "{common.valid.required}")
    private String description;
}
