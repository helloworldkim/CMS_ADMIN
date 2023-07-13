package com.example.cms.web.controller.admingroupmenu;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminGroupMenuForm {
    @NotBlank(message = "{common.valid.required}")
    private String adminGroupId;
    @NotBlank(message = "{common.valid.required}")
    private String name;
    @NotBlank(message = "{common.valid.required}")
    private String description;
}
