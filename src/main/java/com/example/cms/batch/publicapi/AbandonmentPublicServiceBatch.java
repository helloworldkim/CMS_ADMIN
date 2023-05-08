package com.example.cms.batch.publicapi;

//5단계에 따라서 요청되어야 함.
// 1. 시도조회
// 2. 시군구조회 1에서 가져온 시도 코드 필요(필수)
// 3. 보호소 조회 1,2 모두 필요(필수값)
// 4. 품종 조회 //개 : 417000, 고양이 : 422400, 기타 : 429900 필수값
// 5. 유기동물 조회

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AbandonmentPublicServiceBatch {

    //일단 1분마다
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void startBatch() {
        log.info("1분마다 실행 확인!!");

    }

}
