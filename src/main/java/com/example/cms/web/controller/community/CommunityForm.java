package com.example.cms.web.controller.community;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CommunityForm {

    private Long id;
    @NotBlank
    @Size(max = 255, message = "{message.board.community.title}")
    private String title;
    @NotBlank
    @Size(max = 4000, message = "{message.board.community.content}")
    private String content;
}
