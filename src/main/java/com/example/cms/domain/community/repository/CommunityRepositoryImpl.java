package com.example.cms.domain.community.repository;

import com.example.cms.domain.community.dto.CommunityDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.cms.domain.community.entity.QCommunity.community;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityCustomRepository {

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<CommunityDTO> findCommunityList(Pageable pageable) {
        List<CommunityDTO> list = queryFactory
                .select(constructor(
                        CommunityDTO.class,
                        community.id,
                        community.title,
                        community.content,
                        community.createdBy,
                        community.createdDate,
                        community.lastModifiedBy,
                        community.lastModifiedDate))
                .from(community)
                .orderBy(community.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory
                .selectFrom(community)
                .fetchCount();

        return new PageImpl<>(list, pageable, totalCount);
    }
}
