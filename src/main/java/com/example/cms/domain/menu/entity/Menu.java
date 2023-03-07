package com.example.cms.domain.menu.entity;

import com.mysema.commons.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue
    @Column(name = "memu_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;
    private String name;
    private String pathUrl;
    private int listOrder;
    @OneToMany(mappedBy = "parent")
    private List<Menu> children = new ArrayList<>();

    public void setParent(Menu parent) {
        this.parent = parent;
    }
    @Builder
    public Menu(Long id, Menu parent, String name, String pathUrl, int listOrder, List<Menu> children) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.pathUrl = parent == null ? "" : Objects.requireNonNull(pathUrl);
        this.listOrder = listOrder;
        this.children = children == null ? List.of() : children;
    }
}
