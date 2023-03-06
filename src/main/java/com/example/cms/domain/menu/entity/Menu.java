package com.example.cms.domain.menu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Menu {

    @Id
    @GeneratedValue
    @Column(name = "memu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Menu parent;

    private String name;

    private int listOrder;

    @OneToMany(mappedBy = "parent")
    private List<Menu> children = new ArrayList<>();

    @Builder
    public Menu(Long id, Menu parent, String name, int listOrder, List<Menu> children) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.listOrder = listOrder;
        this.children = children;
    }
}
