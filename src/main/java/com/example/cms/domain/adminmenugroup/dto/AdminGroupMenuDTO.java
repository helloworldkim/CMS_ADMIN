package com.example.cms.domain.adminmenugroup.dto;

import lombok.Builder;

public record AdminGroupMenuDTO(
        Long menuId,
        String name,
        String url,
        int listOrder,
        boolean menuAccess
) {
    @Builder
    public AdminGroupMenuDTO(Long menuId, String name, String url, int listOrder, boolean menuAccess) {
        this.menuId = menuId;
        this.name = name;
        this.url = url;
        this.listOrder = listOrder;
        this.menuAccess = menuAccess;
    }
}
