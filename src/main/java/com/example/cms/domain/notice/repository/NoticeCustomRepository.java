package com.example.cms.domain.notice.repository;

import com.example.cms.web.controller.notice.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeCustomRepository {

    Page<NoticeDTO> findNoticeList(Pageable pageable);
}
