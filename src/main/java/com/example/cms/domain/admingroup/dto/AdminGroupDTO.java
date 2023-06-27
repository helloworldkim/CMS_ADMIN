package com.example.cms.domain.admingroup.dto;

import com.example.cms.system.enums.AdminMainAccessType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminGroupDTO {
    private Long adminGroupId;
    private String name;
    private String description;
    private AdminMainAccessType accessType;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
