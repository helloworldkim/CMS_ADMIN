package com.example.cms.domain.menu.dto;

import com.example.cms.domain.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MenuResDTO {

    private Long id;
    private String name;
    private Long parentId;
    private String pathUrl;
    private int listOrder;

    private List<MenuResDTO> children;

    public MenuResDTO(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.parentId = menu.getParent() != null ? menu.getParent().getId() : null;
        this.pathUrl = menu.getPathUrl() != null ? menu.getPathUrl() : "";
        this.listOrder = menu.getListOrder();
        this.children = menu.getChildren().stream().map(MenuResDTO::new).toList();
    }

}