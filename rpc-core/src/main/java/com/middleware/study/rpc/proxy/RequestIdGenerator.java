package com.middleware.study.rpc.proxy;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wuhaitao
 * @date 2016/5/26 21:02
 */
public class RequestIdGenerator {
    private static AtomicLong offset = new AtomicLong(0);
    /**
     * 获取 requestId
     *
     * @return
     */
    public static long getRequestId() {
        return System.currentTimeMillis() * 100000 + offset.incrementAndGet();
    }

}
