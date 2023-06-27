package com.example.cms.web.controller.admin;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.system.enums.AdminRole;
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
public class AdminForm {
    @NotBlank(message = "{common.valid.required}")
    private Long id;
    @NotBlank(message = "{common.valid.required}")
    private String adminId;
    @NotBlank(message = "{common.valid.required}")
    private String adminRole;
    @Pattern(regexp = PWD_REGEX, message = "{common.valid.pwd.format}")
    private String password;
    @NotBlank(message = "{common.valid.required}")
    private String adminGroupId;
    @NotBlank(message = "{common.valid.required}")
    private String adminName;
    @NotBlank(message = "{common.valid.required}")
    @Email(message = "{common.valid.email.format}")
    private String email;
    private boolean deleted; // 삭제 여부 기본값 false
}
