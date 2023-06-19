package com.example.cms.domain.notice.service;

import com.example.cms.domain.notice.entity.Notice;
import com.example.cms.domain.notice.repository.NoticeRepository;
import com.example.cms.web.controller.notice.NoticeDTO;
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

    @Transactional
    public Long save(Notice notice) {
        return noticeRepository.save(notice).getId();
    }


    public Optional<Notice> findById(Long id) {
        return noticeRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        noticeRepository.findById(id).orElseThrow(() -> new IllegalStateException("등록된 공지사항이 없습니다."));
        noticeRepository.deleteById(id);
    }

    public Page<NoticeDTO> findAll(Pageable pageable) {
        return noticeRepository.findNoticeList(pageable);
    }

}
