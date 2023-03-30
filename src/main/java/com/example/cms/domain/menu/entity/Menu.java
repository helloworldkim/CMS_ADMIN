package com.example.cms.domain.menu.entity;

import com.example.cms.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memu_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;
    private String name;
    private String url;
    private int order;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> children = new ArrayList<>();

    public void setParent(Menu parent) {
        if (this.url == null) {
            throw new IllegalArgumentException("상위메뉴가 존재하는 경우 URL은 필수입니다.");
        }
        this.parent = parent;
    }
    @Builder
    public Menu(Long id, Menu parent, String name, String url, int order, List<Menu> children) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.url = url;
        this.order = order;
        this.children = children == null ? List.of() : children;
    }
}
