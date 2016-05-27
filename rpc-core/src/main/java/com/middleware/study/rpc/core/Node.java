package com.middleware.study.rpc.core;

/**
 * @author wuhaitao
 * @date 2016/5/25 21:06
 */
public interface Node {
    void init();
    void destroy();
    boolean isAvailable();
    String desc();
}
