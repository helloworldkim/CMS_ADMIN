package com.example.cms.web.controller.notice;

import com.example.cms.domain.notice.dto.NoticeDTO;
import com.example.cms.domain.notice.entity.Notice;
import com.example.cms.domain.notice.service.NoticeService;
import com.example.cms.system.util.MessageUtil;
import com.example.cms.web.controller.BaseJsonVO;
import com.example.cms.web.controller.Pagination;
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
@RequestMapping("/board/notice")
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final Pagination page;
    private final MessageUtil messageUtil;
    private final String redirectUrl = "redirect:/board/notice";

    /**
     * 목록페이지
     * @param model
     * @return
     */
    @GetMapping("")
    public String menuList(Model model,@PageableDefault(size = 10) Pageable pageable) {
        Page<NoticeDTO> noticeList = noticeService.findAll(pageable);

        Pagination pagination = page.setPagination(noticeList, 10);
        model.addAttribute("noticeList", noticeList.getContent());
        model.addAttribute("pagination", pagination);
        return "/notice/list";
    }

    /**
     * 수정페이지
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public String noticeUpdateForm(Model model, @PathVariable Long id) {
        Notice notice = noticeService.findById(id).orElseThrow(() -> new IllegalArgumentException(messageUtil.getMessage("message.board.common.unknown.target")));
        model.addAttribute("notice", notice.toDTO());
        return "/notice/form";
    }


    /**
     * 공지사항 수정
     * @param noticeForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/edit")
    public String noticeEdit(@Valid @ModelAttribute("notice") NoticeForm noticeForm
            , BindingResult bindingResult) {
        log.debug("==> NoticeForm={}", noticeForm);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "/notice/form";
        }

        Notice notice = Notice.builder()
                .id(noticeForm.getId())
                .title(noticeForm.getTitle())
                .content(noticeForm.getContent())
                .build();
        noticeService.save(notice);

        return redirectUrl;
    }

    /**
     * 등록페이지
     * @param noticeForm
     * @return
     */
    @GetMapping("/register")
    public String noticeCreateForm(@ModelAttribute("notice")NoticeForm noticeForm) {
        return "/notice/register";
    }

    /**
     * 공지사항 등록
     * @param noticeForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public String noticeRegister(@Valid @ModelAttribute("notice")NoticeForm noticeForm
            , BindingResult bindingResult) {
        log.debug("==> NoticeForm={}", noticeForm);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "/notice/register";
        }

        Notice notice = Notice.builder()
                .title(noticeForm.getTitle())
                .content(noticeForm.getContent())
                .build();
        noticeService.save(notice);

        return redirectUrl;
    }

    /**
     * 공지사항 삭제
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{id}")
    public BaseJsonVO deleteNotice(@PathVariable("id") Long id) {
        noticeService.deleteById(id);

        return BaseJsonVO.builder()
                .data(null)
                .build();
    }

}
