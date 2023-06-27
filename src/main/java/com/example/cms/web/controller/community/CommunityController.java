package com.example.cms.web.controller.community;

import com.example.cms.domain.community.dto.CommunityDTO;
import com.example.cms.domain.community.entity.Community;
import com.example.cms.domain.community.service.CommunityService;
import com.example.cms.system.util.MessageUtil;
import com.example.cms.system.BaseJsonVO;
import com.example.cms.system.Pagination;
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
@RequestMapping("/board/community")
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final Pagination page;
    private final MessageUtil messageUtil;
    private final String redirectUrl = "redirect:/board/community";

    /**
     * 목록페이지
     * @param model
     * @return
     */
    @GetMapping("")
    public String communityList(Model model,@PageableDefault(size = 10) Pageable pageable) {
        Page<CommunityDTO> communityList = communityService.findAll(pageable);

        Pagination pagination = page.setPagination(communityList, 10);
        model.addAttribute("communityList", communityList.getContent());
        model.addAttribute("pagination", pagination);
        return "/community/list";
    }

    /**
     * 수정페이지
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public String communityUpdateForm(Model model, @PathVariable Long id) {
        Community community = communityService.findById(id).orElseThrow(() -> new IllegalArgumentException(messageUtil.getMessage("message.board.common.unknown.target")));
        model.addAttribute("community", community.toDTO());
        return "/community/form";
    }


    /**
     * 커뮤니티 수정
     * @param communityForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/edit")
    public String communityEdit(@Valid @ModelAttribute("community") CommunityForm communityForm
            , BindingResult bindingResult) {
        log.debug("==> CommunityForm={}", communityForm);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "/community/form";
        }
        communityService.update(communityForm);

        return redirectUrl;
    }

    /**
     * 등록페이지
     * @param communityForm
     * @return
     */
    @GetMapping("/register")
    public String communityCreateForm(@ModelAttribute("community") CommunityForm communityForm) {
        return "/community/register";
    }

    /**
     * 커뮤니티 등록
     * @param communityForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public String communityRegister(@Valid @ModelAttribute("community") CommunityForm communityForm
            , BindingResult bindingResult) {
        log.debug("==> CommunityForm={}", communityForm);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "/community/register";
        }

        Community community = Community.builder()
                .title(communityForm.getTitle())
                .content(communityForm.getContent())
                .build();
        communityService.save(community);

        return redirectUrl;
    }

    /**
     * 커뮤니티 글 삭제
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{id}")
    public BaseJsonVO deleteNotice(@PathVariable("id") Long id) {
        communityService.deleteById(id);

        return BaseJsonVO.builder()
                .data(null)
                .build();
    }

}
