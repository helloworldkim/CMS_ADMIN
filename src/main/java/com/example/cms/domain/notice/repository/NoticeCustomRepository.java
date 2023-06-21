package com.example.cms.domain.notice.repository;

import com.example.cms.domain.notice.dto.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeCustomRepository {

    Page<NoticeDTO> findNoticeList(Pageable pageable);
}
