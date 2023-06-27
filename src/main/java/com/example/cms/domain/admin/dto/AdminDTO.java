package com.example.cms.domain.admin.dto;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.system.enums.AdminRole;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class AdminDTO {
    private Long id;
    private String adminId;

    @Enumerated(EnumType.STRING)
    private AdminRole adminRole;
    private AdminGroup adminGroup;
    private String adminName;
    private String email;
    private boolean deleted = Boolean.FALSE; // 삭제 여부 기본값 false
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    @Builder
    public AdminDTO(Long id, String adminId, AdminRole adminRole, AdminGroup adminGroup, String adminName, String email, boolean deleted, String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.adminId = adminId;
        this.adminRole = adminRole;
        this.adminGroup = adminGroup;
        this.adminName = adminName;
        this.email = email;
        this.deleted = deleted;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }
}
