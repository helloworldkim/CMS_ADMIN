package com.example.cms.domain.adminmenugroup.dto;

import lombok.Builder;

import java.util.List;

public record AdminGroupMenuDTO(
        Long id,
        String name,
        Long parentId,
        String url,
        int listOrder,
        List<AdminGroupMenuDTO> children,
        boolean menuAccess
) {
    @Builder
    public AdminGroupMenuDTO(Long id, String name, Long parentId, String url, int listOrder, List<AdminGroupMenuDTO> children, boolean menuAccess) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.url = url;
        this.listOrder = listOrder;
        this.children = children;
        this.menuAccess = menuAccess;
    }
}
