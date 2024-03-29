package com.example.cms.domain.admin.entity;

import com.example.cms.domain.admin.dto.AdminDTO;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admin.dto.AuthAdminDTO;
import com.example.cms.domain.common.BaseEntity;
import com.example.cms.system.enums.AdminRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id", nullable = false, unique = true)
    private String adminId;
    @Enumerated(EnumType.STRING)
    private AdminRole adminRole;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_group_id")
    private AdminGroup adminGroup;
    private String password;
    private String adminName;
    private String email;
    private boolean deleted = Boolean.FALSE; // 삭제 여부 기본값 false

    @Builder
    public Admin(Long id, String adminId, AdminRole adminRole, AdminGroup adminGroup, String password, String adminName, String email, boolean deleted) {
        this.id = id;
        this.adminId = Objects.requireNonNull(adminId);
        this.adminRole = Objects.requireNonNull(adminRole);
        this.adminGroup = Objects.requireNonNull(adminGroup);
        this.password = Objects.requireNonNull(password);
        this.adminName = Objects.requireNonNull(adminName);
        this.email = email;
        this.deleted = deleted;
    }

    public void adminDelete() {
        this.deleted = Boolean.TRUE;
    }
    public void adminActive() {
        this.deleted = Boolean.FALSE;
    }
    public void encodePassword(String encodePassword) {
        this.password = password;
    }

    public AuthAdminDTO toAuthAdminDTO() {
        return AuthAdminDTO.builder()
                .adminGroupName(adminGroup.getName())
                .adminGroupId(adminGroup.getId())
                .adminId(adminId)
                .adminName(adminName)
                .adminMainAccessType(adminGroup.getAccessType())
                .email(email)
                .homeUrl("/")
                .build();
    }
    public AdminDTO AdminDTO() {
        return AdminDTO.builder()
                .adminId(adminId)
                .adminRole(adminRole)
                .adminGroup(adminGroup)
                .adminName(adminName)
                .email(email)
                .build();
    }

    private void updateWithPassword(String password, AdminRole adminRole, AdminGroup adminGroup, String adminName, String email) {
        this.password = Objects.requireNonNull(password);
        this.adminRole = Objects.requireNonNull(adminRole);
        this.adminGroup = Objects.requireNonNull(adminGroup);
        this.adminName = Objects.requireNonNull(adminName);
        this.email = Objects.requireNonNull(email);
    }
    public void update(String password, AdminRole adminRole, AdminGroup adminGroup, String adminName, String email) {
        if (password.isBlank()) {
            this.adminRole = Objects.requireNonNull(adminRole);
            this.adminGroup = Objects.requireNonNull(adminGroup);
            this.adminName = Objects.requireNonNull(adminName);
            this.email = Objects.requireNonNull(email);
        } else {
            this.updateWithPassword(password, adminRole, adminGroup, adminName, email);
        }
    }



}
