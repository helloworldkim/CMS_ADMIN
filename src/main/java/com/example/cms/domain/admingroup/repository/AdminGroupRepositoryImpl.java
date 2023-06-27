package com.example.cms.domain.admingroup.repository;

import com.example.cms.domain.admin.dto.AdminDTO;
import com.example.cms.domain.admingroup.dto.AdminGroupDTO;
import com.example.cms.domain.admingroup.entity.QAdminGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.cms.domain.admin.entity.QAdmin.admin;
import static com.example.cms.domain.admingroup.entity.QAdminGroup.*;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class AdminGroupRepositoryImpl implements AdminGroupCustomRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<AdminGroupDTO> findAdminGroupList(Pageable pageable) {
        List<AdminGroupDTO> list = queryFactory
                .select(constructor(
                        AdminGroupDTO.class,
                        adminGroup.id,
                        adminGroup.name,
                        adminGroup.description,
                        adminGroup.accessType,
                        adminGroup.createdBy,
                        adminGroup.createdDate,
                        adminGroup.lastModifiedBy,
                        adminGroup.lastModifiedDate))
                .from(adminGroup)
                .orderBy(adminGroup.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory
                .selectFrom(adminGroup)
                .fetchCount();

        return new PageImpl<>(list, pageable, totalCount);
    }
}
