package com.example.cms.system.aop.logtrace;


import com.example.cms.system.constant.GlobalConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

public class TraceId {

    private       String id;
    private       int    level;
    private final int    FIRST_LEVEL = 1;

    public TraceId() {
        this.id = createId();
        this.level = FIRST_LEVEL;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        String newId;
        String traceId = MDC.get(GlobalConst.REQUEST_TRACE_ID);
        if (StringUtils.isNotBlank(traceId)) {
            newId = traceId.substring(0, 8);
        } else {
            newId = UUID.randomUUID().toString().substring(0, 8);
        }

        return newId;
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == FIRST_LEVEL;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
