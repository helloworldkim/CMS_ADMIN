package com.example.cms.domain.adminmenugroup.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminGroupMenuDTO {
    private Long menuId;
    private String name;
    private String url;
    Integer listOrder;
    boolean menuAccess;
    @Builder
    public AdminGroupMenuDTO(Long menuId, String name, String url, int listOrder, boolean menuAccess) {
        this.menuId = menuId;
        this.name = name;
        this.url = url;
        this.listOrder = listOrder;
        this.menuAccess = menuAccess;
    }

}
