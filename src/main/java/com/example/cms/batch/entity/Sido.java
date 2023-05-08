package com.example.cms.batch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Sido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("orgCd")
    private String orgCd; //시도코드
    @JsonProperty("orgdownNm")
    private String orgdownNm; //시도명
}
