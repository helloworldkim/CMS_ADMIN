package com.example.cms.web.controller.admingroupmenu;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.adminmenugroup.service.AdminGroupMenuService;
import com.example.cms.system.BaseJsonVO;
import com.example.cms.system.Pagination;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/system/group-menu")
@Slf4j
public class AdminGroupMenuController {

    private final AdminGroupMenuService adminGroupMenuService;
    private final Pagination page;
    private final MessageUtil messageUtil;
    private final String redirectUrl = "redirect:/system/group-menu";

    /**
     * 목록페이지
     * @param model
     * @return
     */
    @GetMapping("")
    public String adminList(Model model, @PageableDefault(size = 10) Pageable pageable) {
//        adminGroupMenuService.findAdminMenuGroupList(pageable);

//        Pagination pagination = page.setPagination(adminMenuGroupList, 10);
//        model.addAttribute("adminMenuGroupList", adminMenuGroupList.getContent());
//        model.addAttribute("pagination", pagination);
        return "/groupmenu/list";
    }

    /**
     * 수정페이지
     * @param model
     * @param adminMenuGroupId
     * @return
     */
    @GetMapping("/{adminMenuGroupId}")
    public String adminUpdateForm(Model model, @PathVariable("adminMenuGroupId") Long adminMenuGroupId) {
//        adminGroupMenuService.findById(adminMenuGroupId).orElseThrow(() -> new IllegalArgumentException(messageUtil.getMessage("message.admin.group.common.unknown.target")));
//        model.addAttribute("adminGroup", adminGroup.toDTO());
        return "/groupmenu/form";
    }


    /**
     * 어드민 그룹 수정
     * @param adminGroupMenuForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/edit")
    public String adminEdit(@Valid @ModelAttribute("adminGroup") AdminGroupMenuForm adminGroupMenuForm
            , BindingResult bindingResult
            , Model model) {
        log.debug("==> AdminGroupForm={}", adminGroupMenuForm);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "/groupmenu/form";
        }

        adminGroupMenuService.update(adminGroupMenuForm);

        return redirectUrl;
    }

    /**
     * 등록페이지
     * @param adminGroupMenuRegisterForm
     * @return
     */
    @GetMapping("/register")
    public String adminCreateForm(@ModelAttribute("adminGroup") AdminGroupMenuRegisterForm adminGroupMenuRegisterForm
            , Model model) {
        return "/groupmenu/register";
    }

    /**
     * 어드민 그룹 등록
     * @param adminGroupMenuRegisterForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public String adminRegister(@Valid @ModelAttribute("adminGroup") AdminGroupMenuRegisterForm adminGroupMenuRegisterForm
            , BindingResult bindingResult
            , Model model) {
        log.debug("==> AdminGroupRegisterForm={}", adminGroupMenuRegisterForm);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "/groupmenu/register";
        }

//        adminGroupMenuService.save(adminGroup);

        return redirectUrl;
    }

    /**
     * 어드민 그룹 삭제
     * @param adminMenuGroupId
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{adminMenuGroupId}")
    public BaseJsonVO deleteNotice(@PathVariable("adminMenuGroupId") Long adminMenuGroupId) {
        adminGroupMenuService.deleteById(adminMenuGroupId);

        return BaseJsonVO.builder()
                .data(null)
                .build();
    }

}
