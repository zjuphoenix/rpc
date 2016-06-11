package com.middleware.study.rpc.core;

import java.util.List;
import java.util.Set;

/**
 * @author wuhaitao
 * @date 2016/6/8 22:17
 */
public interface Cluster<T> extends Caller<T> {
    void init(Set<String> urls);
    List<Referer<T>> getReferers();
    Class<T> getServiceInterface();
}
