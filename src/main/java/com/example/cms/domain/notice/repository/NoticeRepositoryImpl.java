package com.example.cms.domain.notice.repository;

import com.example.cms.domain.notice.entity.Notice;
import com.example.cms.domain.notice.entity.QNotice;
import com.example.cms.web.controller.notice.NoticeDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.cms.domain.notice.entity.QNotice.*;
import static com.querydsl.core.types.Projections.*;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeCustomRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<NoticeDTO> findNoticeList(Pageable pageable) {
        List<NoticeDTO> list = queryFactory
                .select(constructor(
                        NoticeDTO.class,
                        notice.id,
                        notice.title,
                        notice.content,
                        notice.createdBy,
                        notice.createdDate,
                        notice.lastModifiedBy,
                        notice.lastModifiedDate))
                .from(notice)
                .orderBy(notice.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory
                .selectFrom(notice)
                .fetchCount();

        return new PageImpl<>(list, pageable, totalCount);
    }
}
