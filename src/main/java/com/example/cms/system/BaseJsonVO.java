package com.example.cms.system;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class BaseJsonVO {
    /** 결과코드 */
    private Integer resultCode = 000;

    /** 메시지 */
    private String msg;

    /** 이동URL */
    private String moveUrl;

    /** 데이터 */
    private Object data;
    /** 에러메시지 */
    private String error;
}
