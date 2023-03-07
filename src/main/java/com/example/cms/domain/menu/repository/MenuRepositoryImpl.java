package com.example.cms.domain.menu.repository;

import com.example.cms.domain.menu.dto.MenuResDTO;
import com.example.cms.domain.menu.entity.Menu;
import com.example.cms.domain.menu.entity.QMenu;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuCustomRepositroy {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MenuResDTO> findMenuListWithQuerydsl() {
        QMenu parent = new QMenu("parent");
        QMenu child = new QMenu("child");

        List<Menu> menuList = queryFactory.select(parent)
                .from(parent)
                .leftJoin(parent.children, child)
                .fetchJoin()
                .where(
                        parent.parent.isNull()
                )
                .orderBy(parent.listOrder.asc(), child.listOrder.asc())
                .fetch();

        return menuList.stream().map(MenuResDTO::new).toList();

    }

}
