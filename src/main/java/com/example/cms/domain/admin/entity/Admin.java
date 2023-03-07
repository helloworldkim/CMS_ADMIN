package com.example.cms.domain.admin.entity;

import com.example.cms.domain.admin.emun.AdminRole;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Admin SET deleted = true WHERE id = ?")
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
}
