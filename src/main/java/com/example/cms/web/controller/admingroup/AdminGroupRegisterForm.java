package com.example.cms.web.controller.admingroup;

import lombok.*;

import javax.validation.constraints.NotBlank;

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
