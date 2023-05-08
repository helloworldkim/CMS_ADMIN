package com.example.cms.batch.publicapi;

import com.example.cms.batch.Item;
import com.example.cms.batch.ResponseDto;
import com.example.cms.batch.entity.AnimalKind;
import com.example.cms.batch.entity.Shelter;
import com.example.cms.batch.entity.Sido;
import com.example.cms.batch.entity.Sigungu;
import com.example.cms.batch.repository.AnimalKindRepository;
import com.example.cms.batch.repository.ShelterRepository;
import com.example.cms.batch.repository.SidoRepository;
import com.example.cms.batch.repository.SigunguRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class AbandonmentPublicServiceBatchTest {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private RestTemplate rt;
    @Autowired
    private SidoRepository sidoRepository;
    @Autowired
    private SigunguRepository sigunguRepository;
    @Autowired
    private AnimalKindRepository animalKindRepository;
    @Autowired
    private ShelterRepository shelterRepository;

    private final String _type = "&_type=json";
    private final String serviceKey = "?serviceKey=BKC8cVQJZmzbzk760iM8pPU0%2B%2FMG35Y95n3SBqKnMgCEob9unhBApWNHXrC1qvgoM0vh3CvuWxWZdeyzF1PVNA%3D%3D";

    @Test
    @DisplayName("시도 조회 및 insert 테스트")
    void sidoInertTest() throws Exception {

        String url = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sido";
        String numOfRows = "&numOfRows=1000";
        String pageNo = "&pageNo=1";

        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(serviceKey);
        sb.append(_type);
        sb.append(numOfRows);
        sb.append(pageNo);

        URI uri = new URI(sb.toString());
        log.info("uri = {}", uri);

        ResponseDto res =  rt.getForObject(uri, ResponseDto.class);
        String resultCode = res.getResponse().getHeader().getResultCode();
        if (!resultCode.equals("00")) {
            log.error("resultCode = {}", resultCode);
        }

        List<Item> item = res.getResponse().getBody().getItems().getItem();
        List<Sido> list = new ArrayList<>();
        for (Item i : item) {
            log.info("i = {}", i);
            Sido sido = om.convertValue(i, Sido.class);
            list.add(sido);
            log.info("madeSido = {}", sido);
        }

        sidoRepository.saveAll(list);
    }

    @Test
    @DisplayName("시군구 조회 및 insert 테스트")
    void sigunguInertTest() throws Exception {

        //임시 시도코드 데이터
        String uprCd = "6110000";
        String url = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sigungu";
        String upr_cd = "&upr_cd="+uprCd;


        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(serviceKey);
        sb.append(_type);
        sb.append(upr_cd);

        URI uri = new URI(sb.toString());
        log.info("uri = {}", uri);

        ResponseDto res =  rt.getForObject(uri, ResponseDto.class);
        String resultCode = res.getResponse().getHeader().getResultCode();
        if (!resultCode.equals("00")) {
            log.error("resultCode = {}", resultCode);
        }

        List<Item> item = res.getResponse().getBody().getItems().getItem();
        List<Sigungu> list = new ArrayList<>();
        for (Item i : item) {
            log.info("i = {}", i);
            Sigungu sigungu = om.convertValue(i, Sigungu.class);
            list.add(sigungu);
            log.info("madeSido = {}", sigungu);
        }

        sigunguRepository.saveAll(list);
    }

    @Test
    @DisplayName("보호소 조회 및 insert 테스트")
    void shelterInertTest() throws Exception {

        String uprCd = "6110000"; //시도코드
        String orgCd = "3220000"; //시군구코드

        String url = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/shelter";
        String upr_cd = "&upr_cd="+uprCd;
        String org_cd = "&org_cd="+orgCd;


        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(serviceKey);
        sb.append(_type);
        sb.append(upr_cd);
        sb.append(org_cd);

        URI uri = new URI(sb.toString());
        log.info("uri = {}", uri);

        ResponseDto res =  rt.getForObject(uri, ResponseDto.class);
        String resultCode = res.getResponse().getHeader().getResultCode();
        if (!resultCode.equals("00")) {
            log.error("resultCode = {}", resultCode);
        }

        List<Item> item = res.getResponse().getBody().getItems().getItem();
        List<Shelter> list = new ArrayList<>();
        for (Item i : item) {
            log.info("i = {}", i);
            Shelter shelter = om.convertValue(i, Shelter.class);
            shelter.changeOrgCdAndUprCde(orgCd, upr_cd);
            list.add(shelter);
            log.info("madeShelter = {}", shelter);
        }

        shelterRepository.saveAll(list);
    }

    @Test
    @DisplayName("품종 조회 insert 테스트")
    void animalKindList() throws Exception {

        //임시 시도코드 데이터 개, 고양이, 기타
        String[] upKindCdArray = "417000,422400,429900".split(",");
        //3개 종류 수행
        for (String upKidCd : upKindCdArray) {
            log.info("품졸별 상세 리스트 조회 시작 = {}", upKidCd);
            String url = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/kind";
            String up_kind_cd = "&up_kind_cd="+upKidCd;

            StringBuilder sb = new StringBuilder();
            sb.append(url);
            sb.append(serviceKey);
            sb.append(_type);
            sb.append(up_kind_cd);

            URI uri = new URI(sb.toString());
            log.info("uri = {}", uri);

            ResponseDto res =  rt.getForObject(uri, ResponseDto.class);
            String resultCode = res.getResponse().getHeader().getResultCode();
            if (!resultCode.equals("00")) {
                log.error("resultCode = {}", resultCode);
            }

            List<Item> item = res.getResponse().getBody().getItems().getItem();
            List<AnimalKind> list = new ArrayList<>();
            for (Item i : item) {
                log.info("i = {}", i);
                AnimalKind kind = om.convertValue(i, AnimalKind.class);
                kind.chageUpKindCde(upKidCd);
                list.add(kind);
                log.info("madeKind = {}", kind);
            }

            animalKindRepository.saveAll(list);

        }





    }
}