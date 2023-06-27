package com.example.cms.domain.admingroup.entity;

import com.example.cms.domain.admingroup.dto.AdminGroupDTO;
import com.example.cms.system.enums.AdminMainAccessType;
import com.example.cms.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_group_id" )
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private AdminMainAccessType accessType;

    @Builder
    public AdminGroup(Long id, String name, String description, AdminMainAccessType accessType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accessType = accessType;
    }

    public AdminGroupDTO toDTO() {
        return AdminGroupDTO.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .createdBy(this.getCreatedBy())
                .createdDate(this.getCreatedDate())
                .lastModifiedBy(this.getLastModifiedBy())
                .lastModifiedDate(this.getLastModifiedDate())
                .build();
    }

}
