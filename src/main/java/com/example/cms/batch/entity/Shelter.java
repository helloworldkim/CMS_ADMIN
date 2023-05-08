package com.example.cms.batch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("uprCd")
    private String uprCd; //시도코드
    @JsonProperty("orgCd")
    private String orgCd; //시군구코드
    @JsonProperty("careRegNo")
    private String careRegNo; //보호소코드
    @JsonProperty("careNm")
    private String careNm; //보호소명

    public void changeOrgCdAndUprCde(String orgCd, String uprCd) {
        this.orgCd = orgCd;
        this.uprCd = uprCd;
    }
}
