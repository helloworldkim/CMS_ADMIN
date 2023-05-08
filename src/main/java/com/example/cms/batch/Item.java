package com.example.cms.batch;

import lombok.Data;

@Data
public class Item {
    private String orgCd; //시도코드
    private String orgdownNm; //시도명,시군구명
    private String uprCd; //시군구코드
    private String careRegNo; //보호소코드
    private String careNm; //보호소명
    private String kindCd; //품종코드
    private String knm; //품종명
}
