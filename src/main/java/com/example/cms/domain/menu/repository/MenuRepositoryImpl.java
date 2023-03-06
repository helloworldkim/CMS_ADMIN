package com.example.cms.domain.menu.repository;

import com.example.cms.domain.menu.entity.Menu;
import com.example.cms.domain.menu.entity.QMenu;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuCustomRepositroy {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Menu> findMenuListWithQuerydsl() {
        QMenu parent = new QMenu("parent");
        QMenu child = new QMenu("child");

        return queryFactory.selectFrom(parent)
//                .distinct()
                .leftJoin(parent.children, child)
                .fetchJoin()
                .where(
                        parent.parent.isNull()
                )
                .orderBy(parent.listOrder.asc(), child.listOrder.asc())
                .fetch();

    }

}
