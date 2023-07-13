package com.example.cms.web.controller.admingroup;

import com.example.cms.domain.admingroup.dto.AdminGroupDTO;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.service.AdminGroupService;
import com.example.cms.system.BaseJsonVO;
import com.example.cms.system.Pagination;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/system/group")
@Slf4j
public class AdminGroupController {

    private final AdminGroupService adminGroupService;
    private final Pagination page;
    private final MessageUtil messageUtil;
    private final String redirectUrl = "redirect:/system/group";

    /**
     * 목록페이지
     * @param model
     * @return
     */
    @GetMapping("")
    public String adminList(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<AdminGroupDTO> adminGroupList = adminGroupService.findAdminGroupList(pageable);

        Pagination pagination = page.setPagination(adminGroupList, 10);
        model.addAttribute("adminGroupList", adminGroupList.getContent());
        model.addAttribute("pagination", pagination);
        return "/group/list";
    }

    /**
     * 수정페이지
     * @param model
     * @param adminGroupId
     * @return
     */
    @GetMapping("/{adminGroupId}")
    public String adminUpdateForm(Model model, @PathVariable("adminGroupId") Long adminGroupId) {
        AdminGroup adminGroup = adminGroupService.findById(adminGroupId)
                .orElseThrow(() -> new IllegalArgumentException(messageUtil.getMessage("message.admin.group.common.unknown.target")));
        model.addAttribute("adminGroup", adminGroup.toDTO());
        return "/group/form";
    }


    /**
     * 어드민 그룹 수정
     * @param adminGroupForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/edit")
    public String adminEdit(@Valid @ModelAttribute("adminGroup") AdminGroupForm adminGroupForm
            , BindingResult bindingResult
            , Model model) {
        log.debug("==> AdminGroupForm={}", adminGroupForm);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "/group/form";
        }

        adminGroupService.update(adminGroupForm);

        return redirectUrl;
    }

    /**
     * 등록페이지
     * @param adminGroupRegisterForm
     * @return
     */
    @GetMapping("/register")
    public String adminCreateForm(@ModelAttribute("adminGroup") AdminGroupRegisterForm adminGroupRegisterForm
            , Model model) {
        return "/group/register";
    }

    /**
     * 어드민 그룹 등록
     * @param adminGroupRegisterForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public String adminRegister(@Valid @ModelAttribute("adminGroup") AdminGroupRegisterForm adminGroupRegisterForm
            , BindingResult bindingResult
            , Model model) {
        log.debug("==> AdminGroupRegisterForm={}", adminGroupRegisterForm);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "/group/register";
        }
        AdminGroup adminGroup = AdminGroup.builder()
                .name(adminGroupRegisterForm.getName())
                .description(adminGroupRegisterForm.getDescription())
                .build();
        adminGroupService.save(adminGroup);

        return redirectUrl;
    }

    /**
     * 어드민 그룹 삭제
     * @param adminGroupId
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{adminGroupId}")
    public BaseJsonVO deleteNotice(@PathVariable("adminGroupId") Long adminGroupId) {
        adminGroupService.deleteById(adminGroupId);

        return BaseJsonVO.builder()
                .data(null)
                .build();
    }

}
