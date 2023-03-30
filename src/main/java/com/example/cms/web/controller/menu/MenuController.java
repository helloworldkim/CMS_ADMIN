package com.example.cms.web.controller.menu;

import com.example.cms.domain.menu.dto.MenuResDTO;
import com.example.cms.domain.menu.entity.Menu;
import com.example.cms.domain.menu.service.MenuService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menu")
    public String menuList(Model model) {
        List<Menu> topLevelMenus = menuService.getTopLevelMenus();
        Map<Long, List<Menu>> childMenusByParentId = new LinkedHashMap<>();
        for (Menu menu : topLevelMenus) {
            menuService.getChildMenusByParentId(childMenusByParentId, menu.getId());
        }
        model.addAttribute("topLevelMenus", topLevelMenus);
        model.addAttribute("childMenusByParentId", childMenusByParentId);
        return "/index";
    }
}
