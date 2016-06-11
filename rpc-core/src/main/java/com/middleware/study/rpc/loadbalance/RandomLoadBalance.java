package com.middleware.study.rpc.loadbalance;

import com.middleware.study.rpc.core.Referer;
import com.middleware.study.rpc.transport.impl.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * @author wuhaitao
 * @date 2016/6/11 18:21
 */
public class RandomLoadBalance<T> implements LoadBalance<T> {

    private List<Referer<T>> referers;

    @Override
    public void onRefresh(List<Referer<T>> referers) {
        this.referers = referers;
    }

    @Override
    public Referer<T> select(RpcRequest request) {
        int size = referers.size();
        Random random = new Random();
        return referers.get(random.nextInt(size));
    }
}
