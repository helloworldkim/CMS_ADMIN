package com.example.cms.web.controller.admin;

import com.example.cms.domain.admin.dto.AdminDTO;
import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.service.AdminService;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.service.AdminGroupService;
import com.example.cms.system.BaseJsonVO;
import com.example.cms.system.Pagination;
import com.example.cms.system.enums.AdminRole;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/system/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final AdminGroupService adminGroupService;
    private final Pagination page;
    private final MessageUtil messageUtil;
    private final PasswordEncoder passwordEncoder;
    private final String redirectUrl = "redirect:/system/admin";

    /**
     * 목록페이지
     * @param model
     * @return
     */
    @GetMapping("")
    public String adminList(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<AdminDTO> adminList = adminService.findAllWithPage(pageable);

        Pagination pagination = page.setPagination(adminList, 10);
        model.addAttribute("adminList", adminList.getContent());
        model.addAttribute("pagination", pagination);
        return "/admin/list";
    }

    /**
     * 수정페이지
     * @param model
     * @param adminId
     * @return
     */
    @GetMapping("/{adminId}")
    public String adminUpdateForm(Model model, @PathVariable("adminId") String adminId) {
        Admin admin = adminService.findByAdminId(adminId).orElseThrow(() -> new IllegalArgumentException(messageUtil.getMessage("message.board.common.unknown.target")));
        List<AdminGroup> adminGroupList = adminGroupService.findAll();

        model.addAttribute("admin", admin.AdminDTO());
        model.addAttribute("adminRoles", AdminRole.values());
        model.addAttribute("adminGroupList", adminGroupList);
        return "/admin/form";
    }


    /**
     * 어드민 수정
     * @param adminForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/edit")
    public String adminEdit(@Valid @ModelAttribute("admin") AdminForm adminForm
            , BindingResult bindingResult
            , Model model) {
        log.debug("==> AdminForm={}", adminForm);
        if (bindingResult.hasErrors()) {
            List<AdminGroup> adminGroupList = adminGroupService.findAll();
            model.addAttribute("adminRoles", AdminRole.values());
            model.addAttribute("adminGroupList", adminGroupList);
            log.debug("==> bindingResult = {}", bindingResult);
            return "/admin/form";
        }

        if (!adminForm.getPassword().isBlank()) {
            adminService.validatePassword(adminForm.getPassword());
        }

        adminService.update(adminForm);

        return redirectUrl;
    }

    /**
     * 등록페이지
     * @param adminForm
     * @return
     */
    @GetMapping("/register")
    public String adminCreateForm(@ModelAttribute("admin") AdminRegisterForm adminForm, Model model) {
        List<AdminGroup> adminGroupList = adminGroupService.findAll();
        model.addAttribute("adminRoles", AdminRole.values());
        model.addAttribute("adminGroupList", adminGroupList);
        return "/admin/register";
    }

    /**
     * 어드민 등록
     * @param adminForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public String adminRegister(@Valid @ModelAttribute("admin") AdminRegisterForm adminForm
            , BindingResult bindingResult
            , Model model) {
        log.debug("==> AdminForm={}", adminForm);
        if (bindingResult.hasErrors()) {
            List<AdminGroup> adminGroupList = adminGroupService.findAll();
            model.addAttribute("adminRoles", AdminRole.values());
            model.addAttribute("adminGroupList", adminGroupList);
            log.debug("==> bindingResult = {}", bindingResult);
            return "/admin/register";
        }


        AdminGroup adminGroup = adminGroupService.findById(Long.valueOf(adminForm.getAdminGroupId())).orElseThrow();
        Admin admin = Admin.builder()
                .adminId(adminForm.getAdminId())
                .adminGroup(adminGroup)
                .adminRole(AdminRole.findAdminRole(adminForm.getAdminRole()))
                .adminName(adminForm.getAdminName())
                .password(passwordEncoder.encode(adminForm.getPassword()))
                .email(adminForm.getEmail())
                .build();
        adminService.save(admin);

        return redirectUrl;
    }

    /**
     * 어드민 삭제
     * @param adminId
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{adminId}")
    public BaseJsonVO deleteNotice(@PathVariable("adminId") String adminId) {
        adminService.deleteByAdminId(adminId);

        return BaseJsonVO.builder()
                .data(null)
                .build();
    }

}
