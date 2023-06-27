package com.example.cms.domain.notice.service;

import com.example.cms.domain.notice.entity.Notice;
import com.example.cms.domain.notice.repository.NoticeRepository;
import com.example.cms.domain.notice.dto.NoticeDTO;
import com.example.cms.system.util.MessageUtil;
import com.example.cms.web.controller.notice.NoticeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MessageUtil messageUtil;

    @Transactional
    public Long save(Notice notice) {
        return noticeRepository.save(notice).getId();
    }
    public void update(NoticeForm noticeForm) {
        Notice notice = noticeRepository.findById(noticeForm.getId()).orElseThrow(() -> new IllegalStateException(messageUtil.getMessage("message.board.common.unknown.target")));
        notice.update(noticeForm.getTitle(), noticeForm.getContent());
    }


    public Optional<Notice> findById(Long id) {
        return noticeRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        noticeRepository.findById(id).orElseThrow(() -> new IllegalStateException(messageUtil.getMessage("message.board.common.unknown.target")));
        noticeRepository.deleteById(id);
    }

    public Page<NoticeDTO> findAll(Pageable pageable) {
        return noticeRepository.findNoticeList(pageable);
    }


}
