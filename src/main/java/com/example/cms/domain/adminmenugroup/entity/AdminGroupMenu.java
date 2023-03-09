package com.example.cms.domain.adminmenugroup.entity;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.common.BaseEntity;
import com.example.cms.domain.menu.entity.Menu;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminGroupMenu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_group_menu_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_group_id")
    private AdminGroup adminGroup;
    private boolean menuAccess = Boolean.FALSE;

    @Builder
    public AdminGroupMenu(Long id, Menu menu, AdminGroup adminGroup) {
        this.id = id;
        this.menu = menu;
        this.adminGroup = adminGroup;
    }

    /**
     * 메뉴 접근권한 변경 처리
     */
    public void updateMenuAccessAuth() {
        this.menuAccess = Boolean.TRUE;
    }
    /**
     * 메뉴 접근권한 변경 처리
     */
    public void deleteMenuAccessAuth() {
        this.menuAccess = Boolean.FALSE;
    }

}
