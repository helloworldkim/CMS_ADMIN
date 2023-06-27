package com.example.cms.domain.notice.service;

import com.example.cms.domain.notice.entity.Notice;
import com.example.cms.domain.notice.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Slf4j
class NoticeServiceTest {

    @Autowired
    private NoticeRepository noticeRepository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private NoticeService noticeService;


    @Test
    @DisplayName("공지사항 1건 등록 확인")
    void saveTest() {
        //given
        Notice entity = Notice.builder()
                .title("제목")
                .content("내용")
                .build();

        //when
        Long id = noticeService.save(entity);
        em.flush();

        //then
        Notice notice = noticeRepository.findById(id).get();

        Assertions.assertThat(entity).isEqualTo(notice);
        Assertions.assertThat(entity.getTitle()).isEqualTo(notice.getTitle());
        Assertions.assertThat(entity.getContent()).isEqualTo(notice.getContent());

    }
    @Test
    @DisplayName("공지사항 1건 조회확인")
    void findByIdTest() {
        //given
        Notice entity = Notice.builder()
                .title("제목")
                .content("내용")
                .build();
        Notice save = noticeRepository.save(entity);
        Long id = save.getId();
        em.flush();
        //when
        Notice notice = noticeService.findById(id).get();

        //then
        Assertions.assertThat(notice).isNotNull();

    }

    @Test
    @DisplayName("공지사항 1건 삭제확인")
    void deleteByIdTest() {
        //given
        Notice entity = Notice.builder()
                .title("제목")
                .content("내용")
                .build();
        Notice save = noticeRepository.save(entity);
        Long id = save.getId();
        em.flush();
        //when
        noticeService.deleteById(id);
        //then
        Assertions.assertThat(noticeRepository.findAll()).hasSize(0);
    }

    @Test
    @DisplayName("공지사항 1건 삭제 실패 확인")
    void deleteByIdTestFailure() {
        //given
        Notice entity = Notice.builder()
                .title("제목")
                .content("내용")
                .build();
        Notice save = noticeRepository.save(entity);
        Long id = save.getId();
        em.flush();
        //when
        noticeService.deleteById(id);
        em.flush();
        //then

        Assertions.assertThatThrownBy(() -> noticeService.deleteById(id)).isInstanceOf(IllegalStateException.class);
    }

}