package com.example.cms.domain.notice.entity;

import com.example.cms.domain.common.BaseEntity;
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
    @Column(name = "content", length = 20000)
    private String content;
    @Builder
    public Notice(Long id, String title, String content) {
        this.id = id;
        this.title = Objects.requireNonNull(title);
        this.content = content;
    }


}
