package com.example.cms.domain.community.entity;

import com.example.cms.domain.common.BaseEntity;
import com.example.cms.domain.community.dto.CommunityDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;
    @Column(name = "title", length = 255, nullable = false)
    private String title;
    @Column(name = "content", length = 4000)
    private String content;
    @Builder
    public Community(Long id, String title, String content) {
        this.id = id;
        this.title = Objects.requireNonNull(title);
        this.content = content;
    }

    //================================================================
    // DTO변환
    //================================================================
    public CommunityDTO toDTO() {
        return CommunityDTO.builder()
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
    // UPDATE처리
    //================================================================
    public void updateCommunity(String title, String content) {
        this.title = Objects.requireNonNull(title);
        this.content = content;
    }
}
