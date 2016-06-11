package com.middleware.study.rpc.loadbalance;

import com.middleware.study.rpc.core.Referer;
import com.middleware.study.rpc.transport.impl.RpcRequest;

import java.util.List;

/**
 * @author wuhaitao
 * @date 2016/6/11 18:07
 */
public interface LoadBalance<T> {
    void onRefresh(List<Referer<T>> referers);
    Referer<T> select(RpcRequest request);
}
