package com.example.cms.domain.adminmenugroup.repository;

import com.example.cms.domain.adminmenugroup.entity.AdminGroupMenu;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.cms.domain.adminmenugroup.entity.QAdminGroupMenu.adminGroupMenu;
import static com.example.cms.domain.menu.entity.QMenu.menu;


@RequiredArgsConstructor
public class AdminGroupMenuRepositoryImpl implements AdminGroupMenuCustomRepositroy {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AdminGroupMenu> findAdminGroupMenuListWithQuerydsl(Long adminGroupId) {

        return queryFactory.select(adminGroupMenu)
                .from(adminGroupMenu)
                .join(adminGroupMenu.menu, menu)
                .where(
                        adminGroupMenu.adminGroup.id.eq(adminGroupId)
                        , adminGroupMenu.menuAccess.isTrue()
                )
                .orderBy(menu.listOrder.asc())
                .fetch();
    }

}
