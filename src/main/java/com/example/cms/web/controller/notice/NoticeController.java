package com.example.cms.web.controller.notice;

import com.example.cms.domain.notice.entity.Notice;
import com.example.cms.domain.notice.service.NoticeService;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final MessageUtil messageUtil;

    /**
     * 목록페이지
     * @param model
     * @return
     */
    @GetMapping("/board/notice")
    public String menuList(Model model, Pageable pageable) {
        Page<Notice> noticeList = noticeService.findAll(pageable);
        List<Notice> list = noticeList.getContent();
        model.addAttribute("noticeList", list);
        return "/notice/list";
    }

    /**
     * 수정페이지
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/board/notice/{id}")
    public String noticeUpdateForm(Model model, @PathVariable Long id) {
        Notice notice = noticeService.findById(id).orElseThrow(() -> new IllegalArgumentException(messageUtil.getMessage("message.board.notice.unknown.target")));
        model.addAttribute("notice", notice);
        return "/notice/form";
    }


    @PostMapping("/board/notice/edit")
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

        return "redirect:" + "/board/notice";
    }

    /**
     * 등록페이지
     * @param noticeForm
     * @return
     */
    @GetMapping("/board/notice/register")
    public String noticeCreateForm(@ModelAttribute("notice")NoticeForm noticeForm) {
        return "/notice/register";
    }
    @PostMapping("/board/notice/register")
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

        return "redirect:" + "/board/notice";
    }


}
