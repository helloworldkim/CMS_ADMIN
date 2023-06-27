package com.example.cms.domain.notice.entity;

import com.example.cms.domain.common.BaseEntity;
import com.example.cms.domain.notice.dto.NoticeDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;
    @Column(name = "title", length = 255, nullable = false)
    private String title;
    @Column(name = "content", length = 4000)
    private String content;
    @Builder
    public Notice(Long id, String title, String content) {
        this.id = id;
        this.title = Objects.requireNonNull(title);
        this.content = content;
    }

    //================================================================
    // DTO변환
    //================================================================
    public NoticeDTO toDTO() {
        return NoticeDTO.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .createdBy(this.getCreatedBy())
                .createdDate(this.getCreatedDate())
                .lastModifiedBy(this.getLastModifiedBy())
                .lastModifiedDate(this.getLastModifiedDate())
                .build();
    }

    //================================================================
    // UPDATE
    //================================================================
    public void update(String title, String content) {
        this.title = Objects.requireNonNull(title);
        this.content = content;
    }
}
