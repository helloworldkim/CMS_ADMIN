package com.example.cms.batch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class AnimalKind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("kindCd")
    private String kindCd; //품종코드
    @JsonProperty("upKindCd")
    private String upKindCd; //최상위 품종 코드
    @JsonProperty("orgdownNm")
    private String knm; //품종명

    public void chageUpKindCde(String upKindCd) {
        this.upKindCd = upKindCd;
    }
}
