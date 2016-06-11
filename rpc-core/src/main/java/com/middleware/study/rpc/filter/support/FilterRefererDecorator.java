package com.middleware.study.rpc.filter.support;

import com.middleware.study.rpc.core.Referer;
import com.middleware.study.rpc.filter.Filter;
import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;

import java.util.List;

/**
 * @author wuhaitao
 * @date 2016/6/3 10:15
 */
public class FilterRefererDecorator<T> {
    private Referer<T> referer;
    private List<Filter> filters;

    public FilterRefererDecorator(Referer<T> referer, List<Filter> filters) {
        this.referer = referer;
        this.filters = filters;
    }

    public Referer<T> getFilterReferer(){
        Referer<T> curReferer = referer;
        if (filters != null){
            for (Filter filter : filters){
                Referer<T> lastReferer = curReferer;
                curReferer = new Referer<T>() {
                    @Override
                    public Class<T> getInterface() {
                        return lastReferer.getInterface();
                    }

                    @Override
                    public String getServiceUrl() {
                        return lastReferer.getServiceUrl();
                    }

                    @Override
                    public RpcResponse call(RpcRequest request) {
                        return filter.request(lastReferer, request);
                    }

                    @Override
                    public void init() {
                        lastReferer.init();
                    }

                    @Override
                    public void destroy() {
                        lastReferer.destroy();
                    }

                    @Override
                    public boolean isAvailable() {
                        return lastReferer.isAvailable();
                    }

                    @Override
                    public String desc() {
                        return lastReferer.desc();
                    }
                };
            }
        }
        return curReferer;
    }
}
