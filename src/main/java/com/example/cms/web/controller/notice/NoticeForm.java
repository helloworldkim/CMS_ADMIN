package com.example.cms.web.controller.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class NoticeForm {

    private Long id;
    @NotBlank
    @Size(max = 255, message = "{message.board.notice.title}")
    private String title;
    @NotBlank
    @Size(max = 4000, message = "{message.board.notice.content}")
    private String content;
}
