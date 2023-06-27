package com.example.cms.domain.admin.repository;

import com.example.cms.domain.admin.dto.AdminDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.cms.domain.admin.entity.QAdmin.admin;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminCustomRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<AdminDTO> findAdminList(Pageable pageable) {
        List<AdminDTO> list = queryFactory
                .select(constructor(
                        AdminDTO.class,
                        admin.id,
                        admin.adminId,
                        admin.adminRole,
                        admin.adminGroup,
                        admin.adminName,
                        admin.email,
                        admin.deleted,
                        admin.createdBy,
                        admin.createdDate,
                        admin.lastModifiedBy,
                        admin.lastModifiedDate))
                .from(admin)
                .orderBy(admin.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory
                .selectFrom(admin)
                .fetchCount();

        return new PageImpl<>(list, pageable, totalCount);

    }
}
