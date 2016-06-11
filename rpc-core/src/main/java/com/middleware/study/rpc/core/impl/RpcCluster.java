package com.middleware.study.rpc.core.impl;

import com.middleware.study.rpc.core.Cluster;
import com.middleware.study.rpc.core.Referer;
import com.middleware.study.rpc.filter.Filter;
import com.middleware.study.rpc.filter.impl.MonitorFilter;
import com.middleware.study.rpc.filter.support.FilterRefererDecorator;
import com.middleware.study.rpc.loadbalance.LoadBalance;
import com.middleware.study.rpc.loadbalance.RandomLoadBalance;
import com.middleware.study.rpc.registry.NotifyListener;
import com.middleware.study.rpc.transport.exception.FrameworkException;
import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuhaitao
 * @date 2016/6/8 22:21
 */
public class RpcCluster<T> implements Cluster<T>, NotifyListener {
    private List<Referer<T>> referers = new ArrayList<>();
    private ConcurrentHashMap<String, Referer<T>> refererMap = new ConcurrentHashMap<>();
    private Class<T> clz;
    private LoadBalance<T> loadBalance;

    public RpcCluster(Class<T> clz) {
        this.clz = clz;
        this.loadBalance = new RandomLoadBalance<>();
    }

    @Override
    public void init(Set<String> urls) {
        notify(clz.getName(), urls);
    }

    @Override
    public List<Referer<T>> getReferers() {
        return referers;
    }

    @Override
    public Class<T> getServiceInterface() {
        return clz;
    }

    @Override
    public RpcResponse call(RpcRequest request) {
        if (referers != null && !referers.isEmpty()) {
            Referer<T> referer = loadBalance.select(request);
            return referer.call(request);
        }
        else{
            RpcResponse response = new RpcResponse();
            response.setRequestId(request.getRequestId());
            response.setException(new FrameworkException("no referers available!"));
            return response;
        }
    }

    @Override
    public synchronized void notify(String service, Set<String> urls) {
        for (String url : urls){
            if (!refererMap.containsKey(url)){
                String[] strs = url.split(":");
                Referer<T> basicReferer = new RpcReferer<>(clz, strs[0], Integer.parseInt(strs[1]));
                List<Filter> filters = new ArrayList<>();
                filters.add(new MonitorFilter());
                FilterRefererDecorator<T> filterRefererDecorator = new FilterRefererDecorator<>(basicReferer, filters);
                Referer<T> filterReferer = filterRefererDecorator.getFilterReferer();
                filterReferer.init();
                referers.add(filterReferer);
                refererMap.put(filterReferer.getServiceUrl(), filterReferer);
            }
        }
        loadBalance.onRefresh(referers);
    }
}
